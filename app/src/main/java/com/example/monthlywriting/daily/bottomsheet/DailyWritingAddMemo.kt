package com.example.monthlywriting.daily.bottomsheet

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.monthlywriting.R
import com.example.monthlywriting.daily.viewmodel.DailyWritingAddMemoViewModel
import com.example.monthlywriting.daily.viewmodel.DailyWritingBottomSheetViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingAddMemoBinding
import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.util.checkPermission
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DailyWritingAddMemo : DialogFragment() {

    private lateinit var binding : FragmentDailyWritingAddMemoBinding

    private val bottomSheetViewModel : DailyWritingBottomSheetViewModel by activityViewModels()
    private val addMemoViewModel : DailyWritingAddMemoViewModel by viewModels()
    private val args : DailyWritingBottomSheetArgs by navArgs()

    private lateinit var galleryLauncher : ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_writing_add_memo, container, false)
        binding.viewModel = addMemoViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setCurrentDate()

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){

                val uri = it.data!!.data
                if (uri != null){
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, uri))
                    } else {
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                    }

                    addMemoViewModel.photo.value = bitmap
                } else {
                    Toast.makeText(requireContext(), "이미지를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    private fun setCurrentDate() {
        binding.apply {
            dailyMemoMonth.text = SimpleDateFormat("M", Locale.getDefault()).format(Date(System.currentTimeMillis()))

            dailyMemoDate.minValue = 1
            dailyMemoDate.maxValue = getCurrentEndDateOfMonth()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            dailyMemoCancel.setOnClickListener {
                dismiss()
            }

            dailyMemoSave.setOnClickListener {
                if(bottomSheetViewModel.currentItem.value?.dailymemo!!.any{
                    it.date == addMemoViewModel.date.value!! }) {
                    Toast.makeText(requireContext(), "다른 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(requireContext(), "수정을 원하시면 해당 날짜의 기록을 클릭하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    if (viewModel?.content?.value == null){
                        Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val memo = DailyMemo(
                            date = addMemoViewModel.date.value!!,
                            memo = addMemoViewModel.content.value!!,
                            photo = addMemoViewModel.photo.value
                        )
                        bottomSheetViewModel.saveMemo(args.id, memo)

                        Toast.makeText(requireContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }

            dailyMemoPhoto.setOnClickListener {
                checkPermission(requireContext()) { launchGallery() }
            }
        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.fullscreen_dialog)
    }

    private fun getCurrentEndDateOfMonth() : Int {
        val cal = Calendar.getInstance()
        val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()
        val currentMonth = SimpleDateFormat("MM", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()
        val currentDay = SimpleDateFormat("dd", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt()
        cal.set( currentYear, currentMonth - 1, currentDay )

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
}
package com.example.monthlywriting.daily.bottomsheet

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DailyWritingAddMemo : DialogFragment() {

    private lateinit var binding : FragmentDailyWritingAddMemoBinding

    private val bottomSheetViewModel : DailyWritingBottomSheetViewModel by activityViewModels()
    private val addMemoViewModel : DailyWritingAddMemoViewModel by viewModels()
    private val args : DailyWritingBottomSheetArgs by navArgs()

    private lateinit var galleryLauncher : ActivityResultLauncher<Intent>
    private var imgPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_writing_add_memo, container, false)
        binding.viewModel = addMemoViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setCurrentDate()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.fullscreen_dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val currentItem = bottomSheetViewModel.currentItem.value
        val currentMonth = currentItem?.month

        binding.apply {

            dailyMemoCancel.setOnClickListener {
                dismiss()
            }

            dailyMemoSave.setOnClickListener {
                val currentDate = addMemoViewModel.date.value

                if(bottomSheetViewModel.currentItem.value?.dailymemo!!.any{
                        it.date == currentDate!! }) {
                    Toast.makeText(requireContext(), "다른 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
                    Toast.makeText(requireContext(), "수정을 원하시면 해당 날짜의 기록을 클릭하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    if (viewModel?.content?.value == null || viewModel?.content?.value == ""){
                        Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        saveBitmapInFile(addMemoViewModel.photo.value, "${currentMonth}_${currentDate}")

                        val memo = DailyMemo(
                            date = addMemoViewModel.date.value!!,
                            memo = addMemoViewModel.content.value!!,
                            imgpath = imgPath
                        )

                        bottomSheetViewModel.saveNewMemo(args.id, memo)

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

    private fun setCurrentDate() {
        binding.apply {
            dailyMemoMonth.text = SimpleDateFormat("M", Locale.getDefault()).format(Date(System.currentTimeMillis()))

            dailyMemoDate.minValue = 1
            dailyMemoDate.maxValue = getCurrentEndDateOfMonth()
        }
    }

    private fun saveBitmapInFile(bitmap: Bitmap?, filename: String) {
        if (bitmap != null){
            val file = File(this@DailyWritingAddMemo.context?.filesDir, filename)
            var outputStream : OutputStream? = null

            try {
                file.createNewFile()
                outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
            } finally {
                outputStream?.close()
            }

            imgPath = file.absolutePath
        }
    }

    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
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
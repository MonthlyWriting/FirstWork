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
import com.example.monthlywriting.util.CurrentInfo.Companion.currentMonth
import com.example.monthlywriting.util.CurrentInfo.Companion.getCurrentEndDateOfMonth
import com.example.monthlywriting.util.checkPermission
import com.example.monthlywriting.util.resizeBitmap
import com.example.monthlywriting.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

@AndroidEntryPoint
class DailyWritingAddMemo : DialogFragment() {

    private lateinit var binding: FragmentDailyWritingAddMemoBinding

    private val bottomSheetViewModel: DailyWritingBottomSheetViewModel by activityViewModels()
    private val addMemoViewModel: DailyWritingAddMemoViewModel by viewModels()
    private val args: DailyWritingBottomSheetArgs by navArgs()

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var imgPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_daily_writing_add_memo,
            container,
            false
        )
        binding.viewModel = addMemoViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        setCurrentDate()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), R.style.fullscreen_dialog)
        dialog.window?.attributes?.windowAnimations = R.style.animation_slide_right
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val uri = it.data!!.data
                    if (uri != null) {
                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(
                                ImageDecoder.createSource(
                                    requireContext().contentResolver,
                                    uri
                                )
                            )
                        } else {
                            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                        }

                        addMemoViewModel.photo.value = resizeBitmap(bitmap, 1000)

                    } else {
                        getString(R.string.toast_image_load_failed).toast(requireContext())
                    }
                }
            }

        binding.apply {

            dailyMemoCancel.setOnClickListener {
                dismiss()
            }

            dailyMemoSave.setOnClickListener {
                val currentDate = addMemoViewModel.date.value

                if (bottomSheetViewModel.currentItem.value?.dailymemo!!.any {
                        it.date == currentDate!!
                    }) {
                    getString(R.string.toast_date_duplicated).toast(requireContext())
                } else {
                    if (viewModel?.content?.value == null || viewModel?.content?.value == "") {
                        getString(R.string.toast_no_content).toast(requireContext())
                    } else {
                        saveBitmapInFile(
                            addMemoViewModel.photo.value,
                            "${currentMonth}_${currentDate}"
                        )

                        val memo = DailyMemo(
                            date = addMemoViewModel.date.value!!,
                            memo = addMemoViewModel.content.value!!,
                            imgpath = imgPath
                        )

                        bottomSheetViewModel.saveNewMemo(args.id, memo)

                        getString(R.string.toast_save_done).toast(requireContext())
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
            dailyMemoMonth.text = currentMonth.toString()

            dailyMemoDate.minValue = 1
            dailyMemoDate.maxValue = getCurrentEndDateOfMonth()
        }
    }

    private fun saveBitmapInFile(bitmap: Bitmap?, filename: String) {
        if (bitmap != null) {
            val file = File(this@DailyWritingAddMemo.context?.filesDir, filename)
            var outputStream: OutputStream? = null

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
}
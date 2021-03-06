package com.example.monthlywriting.daily.bottomsheet

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.monthlywriting.R
import com.example.monthlywriting.daily.viewmodel.DailyWritingBottomSheetViewModel
import com.example.monthlywriting.daily.viewmodel.DailyWritingDetailMemoViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingDetailMemoBinding
import android.graphics.BitmapFactory
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.util.CurrentInfo.Companion.currentMonth
import com.example.monthlywriting.util.checkPermission
import com.example.monthlywriting.util.resizeBitmap
import com.example.monthlywriting.util.toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream


class DailyWritingDetailMemo : DialogFragment() {

    private lateinit var binding: FragmentDailyWritingDetailMemoBinding

    private val bottomSheetViewModel: DailyWritingBottomSheetViewModel by activityViewModels()
    private val detailMemoViewModel: DailyWritingDetailMemoViewModel by viewModels()
    private val args: DailyWritingDetailMemoArgs by navArgs()

    private lateinit var content: String
    private var photo: Bitmap? = null

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var imgPath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_daily_writing_detail_memo,
            container,
            false
        )
        binding.viewModel = detailMemoViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        initMemo()
        setupDisplay("normal")

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.fullscreen_dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            dailyDetailMemoCancel.setOnClickListener {
                dismiss()
            }

            dailyDetailMemoEdit.setOnClickListener {
                setupDisplay("edit")
                getString(R.string.daily_memo_detail_edit).toast(requireContext())
            }

            dailyDetailMemoSave.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage(getString(R.string.alert_save))
                    .setPositiveButton(
                        getString(R.string.save)
                    ) { _, _ ->
                        setupDisplay("normal")
                        content = viewModel?.content?.value!!
                        photo = viewModel?.photo?.value

                        saveEditedMemo()
                    }
                    .setNegativeButton(getString(R.string.cancel), null)
                    .show()
            }

            dailyDetailMemoCancelEdit.setOnClickListener {
                setupDisplay("normal")
                viewModel?.content?.value = content
                viewModel?.photo?.value = photo
            }

            dailyDetailMemoPhoto.setOnClickListener {
                checkPermission(requireContext()) { launchGallery() }
            }
        }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
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

                        detailMemoViewModel.photo.value = resizeBitmap(bitmap, 1000)

                    } else {
                        getString(R.string.toast_image_load_failed).toast(requireContext())
                    }
                }
            }
    }

    private fun initMemo() {
        val list = bottomSheetViewModel.currentItem.value!!.dailymemo.filter {
            it.date == args.date
        }

        list[0].apply {
            binding.dailyDetailMemoTitle.text = bottomSheetViewModel.currentItem.value!!.name
            binding.dailyDetailMemoDate.text =
                getString(R.string.detail_memo_date, currentMonth, this.date)
            detailMemoViewModel.content.value = this.memo
            content = this.memo

            if (this.imgpath != null) {
                val f = File(imgpath!!)
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)
                detailMemoViewModel.photo.value = bitmap
                photo = bitmap
            }
        }
    }

    private fun setupDisplay(mode: String) {
        binding.apply {
            when (mode) {
                "edit" -> {
                    dailyDetailMemoCancel.visibility = View.GONE
                    dailyDetailMemoEdit.visibility = View.GONE
                    dailyDetailMemoSave.visibility = View.VISIBLE
                    dailyDetailMemoCancelEdit.visibility = View.VISIBLE

                    dailyDetailMemoPhoto.isEnabled = true
                    dailyDetailMemoContent.isEnabled = true
                }

                "normal" -> {
                    dailyDetailMemoCancel.visibility = View.VISIBLE
                    dailyDetailMemoEdit.visibility = View.VISIBLE
                    dailyDetailMemoSave.visibility = View.GONE
                    dailyDetailMemoCancelEdit.visibility = View.GONE

                    dailyDetailMemoPhoto.isEnabled = false
                    dailyDetailMemoContent.isEnabled = false
                }
            }
        }
    }

    private fun saveEditedMemo() {
        val currentContent = detailMemoViewModel.content.value

        if (currentContent == null || currentContent == "") {
            getString(R.string.toast_no_content).toast(requireContext())
        } else {
            saveBitmapInFile(detailMemoViewModel.photo.value, "${currentMonth}_${args.date}")

            val memo = DailyMemo(
                date = args.date,
                memo = currentContent,
                imgpath = imgPath
            )

            bottomSheetViewModel.updateMemo(bottomSheetViewModel.currentItem.value?.id!!, memo)

            getString(R.string.toast_save_done).toast(requireContext())
            dismiss()
        }
    }

    private fun saveBitmapInFile(bitmap: Bitmap?, filename: String) {
        if (bitmap != null) {
            val file = File(this@DailyWritingDetailMemo.context?.filesDir, filename)
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
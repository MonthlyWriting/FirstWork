package com.example.monthlywriting.monthly

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.FragmentMonthlyWritingDetailMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream

@AndroidEntryPoint
class MonthlyWritingDetailMemo : DialogFragment() {

    private lateinit var binding: FragmentMonthlyWritingDetailMemoBinding
    private val viewModel: MonthlyWritingDetailMemoViewModel by viewModels()

    private val args: MonthlyWritingDetailMemoArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthlyWritingDetailMemoBinding.inflate(layoutInflater)

        viewModel.getItem(args.id)
        setObserver()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.fullscreen_dialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.monthlyDetailMemoCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun setObserver() {
        viewModel.item.observe(viewLifecycleOwner) {
            binding.monthlyDetailMemoTitle.text = it.name
            binding.monthlyDetailMemoDate.text = getString(R.string.date, it.month, args.date)

            val dailyMemo = it.dailymemo.filter { dailyMemo -> dailyMemo.date == args.date }[0]
            binding.monthlyDetailMemoContent.text = dailyMemo.memo

            if (dailyMemo.imgpath != null) {
                val f = File(dailyMemo.imgpath!!)
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)

                binding.monthlyDetailMemoPhoto.setImageBitmap(bitmap)
            }
        }
    }

}
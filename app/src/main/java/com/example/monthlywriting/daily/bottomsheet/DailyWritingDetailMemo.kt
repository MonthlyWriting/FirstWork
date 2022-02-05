package com.example.monthlywriting.daily.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.monthlywriting.R
import com.example.monthlywriting.daily.viewmodel.DailyWritingBottomSheetViewModel
import com.example.monthlywriting.daily.viewmodel.DailyWritingDetailMemoViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingDetailMemoBinding

class DailyWritingDetailMemo : DialogFragment() {

    private lateinit var binding : FragmentDailyWritingDetailMemoBinding

    private val bottomSheetViewModel : DailyWritingBottomSheetViewModel by activityViewModels()
    private val detailMemoViewModel : DailyWritingDetailMemoViewModel by viewModels()
    private val args : DailyWritingDetailMemoArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_writing_detail_memo, container, false)
        binding.viewModel = detailMemoViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        initMemo()

        return binding.root
    }

    private fun initMemo() {
        val month = bottomSheetViewModel.currentItem.value!!.month
        val list = bottomSheetViewModel.currentItem.value!!.dailymemo.filter{
            it.date == args.date
        }

        list[0].apply {
            binding.dailyDetailMemoDate.text = getString(R.string.detail_memo_date, month, this.date)
            binding.dailyDetailMemoContent.text = this.memo
            if (this.imgpath != null){
                Glide.with(requireContext())
                    .load(this.imgpath)
                    .error(R.drawable.ic_baseline_photo_library_24)
                    .placeholder(R.drawable.ic_baseline_photo_library_24)
                    .into(binding.dailyDetailMemoPhoto)
            } else {
                binding.dailyDetailMemoPhoto.setImageResource(R.drawable.ic_baseline_photo_library_24)
            }
        }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.fullscreen_dialog)
    }

}
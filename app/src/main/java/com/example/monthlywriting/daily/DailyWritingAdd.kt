package com.example.monthlywriting.daily

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.monthlywriting.MainActivity
import com.example.monthlywriting.R
import com.example.monthlywriting.daily.viewmodel.DailyWritingAddViewModel
import com.example.monthlywriting.daily.viewmodel.DailyWritingViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyWritingAdd : Fragment() {

    private lateinit var binding : FragmentDailyWritingAddBinding
    private val viewModel : DailyWritingAddViewModel by viewModels()

    private val args : DailyWritingAddArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_writing_add, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        (activity as MainActivity).setDailyWritingTitle()
        setDisplayByType()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dailyWritingSave.setOnClickListener {
            it.findNavController().navigate(DailyWritingAddDirections.closeAdd())
            saveWriting()
        }
    }

    private fun setDisplayByType() {
        listOf(
            binding.rbTypeDaily,
            binding.rbTypeWeekly,
            binding.rbTypeMonthly)
            .forEach {
                it.isEnabled = false
            }

        binding.apply {
            when(args.type){
                "daily" -> {
                    rbTypeDaily.isChecked = true

                    addWeeklyInfo.visibility = View.GONE
                    addMonthlyInfo.visibility = View.GONE
                }
                "weekly" -> {
                    rbTypeWeekly.isChecked = true

                    addWeeklyInfo.visibility = View.VISIBLE
                    addMonthlyInfo.visibility = View.GONE

                    dailyWritingWeekNum.minValue = 1
                    dailyWritingWeekNum.maxValue = 6
                }
                "monthly" -> {
                    rbTypeMonthly.isChecked = true

                    addWeeklyInfo.visibility = View.GONE
                    addMonthlyInfo.visibility = View.VISIBLE

                    dailyWritingMonthTimesText.setTextColor(Color.WHITE)
                    dailyWritingMonthThroughoutText.setTextColor(Color.GRAY)
                    dailyWritingMonthNum.isEnabled = true

                    dailyWritingMonthNum.minValue = 1
                    dailyWritingMonthNum.maxValue = 31

                    rbMonthInfo.setOnCheckedChangeListener { _, id ->
                        when(id){
                            R.id.rb_month_times -> {
                                dailyWritingMonthTimesText.setTextColor(Color.WHITE)
                                dailyWritingMonthThroughoutText.setTextColor(Color.GRAY)
                                dailyWritingMonthNum.isEnabled = true
                            }
                            R.id.rb_month_throughout -> {
                                dailyWritingMonthTimesText.setTextColor(Color.GRAY)
                                dailyWritingMonthThroughoutText.setTextColor(Color.WHITE)
                                dailyWritingMonthNum.isEnabled = false
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveWriting() {
        when(args.type){
            "daily" -> {

            }
            "weekly" -> {

            }
            "monthly" -> {

            }
        }
        Toast.makeText(this.context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
    }
}
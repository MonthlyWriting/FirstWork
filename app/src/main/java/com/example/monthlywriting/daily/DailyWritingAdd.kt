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
import com.example.monthlywriting.databinding.FragmentDailyWritingAddBinding
import com.example.monthlywriting.model.DailyWritingItem
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

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
            saveWriting(it)
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
                    dailyWritingMonthNum.maxValue = getCurrentEndDateOfMonth()

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

    private fun saveWriting(view: View) {
        if(viewModel.name.value != null && viewModel.name.value!!.isNotEmpty()){
            val doneList = MutableList(getCurrentEndDateOfMonth()) { false }
            when(args.type){
                "daily" -> {
                    val newItem = DailyWritingItem(
                        id = 0,
                        month = SimpleDateFormat("MM", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt(),
                        type = "daily",
                        name = viewModel.name.value!!,
                        weektimes = null,
                        monthtimes = null,
                        done = doneList,
                        dailymemo = mutableListOf()
                    )
                    viewModel.insertNewItem(newItem)
                }
                "weekly" -> {
                    val newItem = DailyWritingItem(
                        id = 0,
                        month = SimpleDateFormat("MM", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt(),
                        type = "weekly",
                        name = viewModel.name.value!!,
                        weektimes = viewModel.timesAWeek.value,
                        monthtimes = null,
                        done = doneList,
                        dailymemo = mutableListOf()
                    )
                    viewModel.insertNewItem(newItem)
                }
                "monthly" -> {
                    val monthTimes =
                        if (binding.rbMonthTimes.isChecked) viewModel.timesAMonth.value
                        else 0

                    val newItem = DailyWritingItem(
                        id = 0,
                        month = SimpleDateFormat("MM", Locale.getDefault()).format(Date(System.currentTimeMillis())).toInt(),
                        type = "monthly",
                        name = viewModel.name.value!!,
                        weektimes = null,
                        monthtimes = monthTimes,
                        done = doneList,
                        dailymemo = mutableListOf()
                    )
                    viewModel.insertNewItem(newItem)
                }
            }

            Toast.makeText(this.context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            view.findNavController().navigate(DailyWritingAddDirections.closeAdd())

        } else{
            Toast.makeText(this.context, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
        }
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
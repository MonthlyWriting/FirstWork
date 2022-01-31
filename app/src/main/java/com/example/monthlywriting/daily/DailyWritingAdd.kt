package com.example.monthlywriting.daily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.monthlywriting.MainActivity
import com.example.monthlywriting.databinding.FragmentDailyWritingAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyWritingAdd : Fragment() {

    private lateinit var binding : FragmentDailyWritingAddBinding

    private val args : DailyWritingAddArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyWritingAddBinding.inflate(inflater, container, false)

        (activity as MainActivity).setDailyWritingTitle()
        setDisplayByType()

        return binding.root
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
                }
                "monthly" -> {
                    rbTypeMonthly.isChecked = true

                    addWeeklyInfo.visibility = View.GONE
                    addMonthlyInfo.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dailyWritingSave.setOnClickListener {
            Toast.makeText(this.context, "저장되었습니다.", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(DailyWritingAddDirections.closeAdd())
        }
    }

}
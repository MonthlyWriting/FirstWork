package com.example.monthlywriting.daily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthlywriting.databinding.FragmentDailyWritingBinding

class DailyWritingFragment : Fragment() {

    private lateinit var binding: FragmentDailyWritingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyWritingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTempAdapter()
    }

    fun dummyList(): List<String> {
        return listOf()
    }

    fun setTempAdapter(){
        if (dummyList().isEmpty()){
            binding.dailyWritingItemDaily.visibility = View.GONE
            binding.dailyWritingTextDaily.visibility = View.VISIBLE
        } else {
            val dailyAdapter = DailyWritingItemAdapter(dummyList())
            binding.dailyWritingItemDaily.apply{
                adapter = dailyAdapter
                layoutManager = LinearLayoutManager(this@DailyWritingFragment.context)
            }
        }

        if (dummyList().isEmpty()){
            binding.dailyWritingItemWeekly.visibility = View.GONE
            binding.dailyWritingTextWeekly.visibility = View.VISIBLE
        } else {
            val dailyAdapter = DailyWritingItemAdapter(dummyList())
            binding.dailyWritingItemWeekly.apply{
                adapter = dailyAdapter
                layoutManager = LinearLayoutManager(this@DailyWritingFragment.context)
            }
        }

        if (dummyList().isEmpty()){
            binding.dailyWritingItemMonthly.visibility = View.GONE
            binding.dailyWritingTextMonthly.visibility = View.VISIBLE
        } else {
            val dailyAdapter = DailyWritingItemAdapter(dummyList())
            binding.dailyWritingItemMonthly.apply{
                adapter = dailyAdapter
                layoutManager = LinearLayoutManager(this@DailyWritingFragment.context)
            }
        }
    }
}
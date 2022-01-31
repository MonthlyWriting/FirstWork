package com.example.monthlywriting.daily

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthlywriting.MainActivity
import com.example.monthlywriting.databinding.FragmentDailyWritingMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyWritingMainFragment : Fragment() {

    private lateinit var binding: FragmentDailyWritingMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyWritingMainBinding.inflate(layoutInflater)

        (activity as MainActivity).setDailyWritingTitle()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTempAdapter()
        setOnClick()
    }

    private fun setOnClick() {
        binding.apply {
            listOf(
                dailyWritingTextDaily,
                dailyWritingItemDaily).forEach { item ->
                item.setOnClickListener {
                    val action = DailyWritingMainFragmentDirections.openAdd("daily")
                    it.findNavController().navigate(action)
                }
            }

            listOf(
                dailyWritingTextWeekly,
                dailyWritingItemWeekly).forEach { item ->
                item.setOnClickListener {
                    val action = DailyWritingMainFragmentDirections.openAdd("weekly")
                    it.findNavController().navigate(action)
                }
            }

            listOf(
                dailyWritingTextMonthly,
                dailyWritingItemMonthly).forEach { item ->
                item.setOnClickListener {
                    val action = DailyWritingMainFragmentDirections.openAdd("monthly")
                    it.findNavController().navigate(action)
                }
            }
        }

    }

    private fun dummyList(): List<String> {
        return listOf()
    }

    private fun setTempAdapter(){
        if (dummyList().isEmpty()){
            binding.dailyWritingItemDaily.visibility = View.GONE
            binding.dailyWritingTextDaily.visibility = View.VISIBLE
        } else {
            val dailyAdapter = DailyWritingItemAdapter(dummyList())
            binding.dailyWritingItemDaily.apply{
                adapter = dailyAdapter
                layoutManager = LinearLayoutManager(this@DailyWritingMainFragment.context)
            }
        }

        if (dummyList().isEmpty()){
            binding.dailyWritingItemWeekly.visibility = View.GONE
            binding.dailyWritingTextWeekly.visibility = View.VISIBLE
        } else {
            val dailyAdapter = DailyWritingItemAdapter(dummyList())
            binding.dailyWritingItemWeekly.apply{
                adapter = dailyAdapter
                layoutManager = LinearLayoutManager(this@DailyWritingMainFragment.context)
            }
        }

        if (dummyList().isEmpty()){
            binding.dailyWritingItemMonthly.visibility = View.GONE
            binding.dailyWritingTextMonthly.visibility = View.VISIBLE
        } else {
            val dailyAdapter = DailyWritingItemAdapter(dummyList())
            binding.dailyWritingItemMonthly.apply{
                adapter = dailyAdapter
                layoutManager = LinearLayoutManager(this@DailyWritingMainFragment.context)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("test", "destroyed")
    }
}
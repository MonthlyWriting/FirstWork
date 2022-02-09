package com.example.monthlywriting.daily.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthlywriting.R
import com.example.monthlywriting.daily.viewmodel.DailyWritingItemViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingItemBinding
import com.example.monthlywriting.util.CurrentInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyWritingItemFragment(private val type: String) : Fragment() {

    private lateinit var binding: FragmentDailyWritingItemBinding
    private val viewModel: DailyWritingItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyWritingItemBinding.inflate(layoutInflater)

        getWritingList()
        setDisplay()

        return binding.root
    }

    private fun getWritingList() {
        viewModel.getWritingList(CurrentInfo.currentMonth)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.getWritingList(CurrentInfo.currentMonth)
        }, 2000)
    }

    private fun setDisplay() {
        when (type) {
            "daily" -> {
                binding.dailyWritingType.text = resources.getString(R.string.daily)
                viewModel.dailyList.observe(viewLifecycleOwner) {
                    val adapter = DailyWritingItemAdapter(it)
                    binding.dailyWritingItem.apply {
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
            "weekly" -> {
                binding.dailyWritingType.text = getString(R.string.weekly)
                viewModel.weeklyList.observe(viewLifecycleOwner) {
                    val adapter = DailyWritingItemAdapter(it)
                    binding.dailyWritingItem.apply {
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
            "monthly" -> {
                binding.dailyWritingType.text = getString(R.string.monthly)
                viewModel.monthlyList.observe(viewLifecycleOwner) {
                    val adapter = DailyWritingItemAdapter(it)
                    binding.dailyWritingItem.apply {
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
        }
    }
}
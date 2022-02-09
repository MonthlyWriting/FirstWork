package com.example.monthlywriting.daily.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthlywriting.MainActivity
import com.example.monthlywriting.R
import com.example.monthlywriting.daily.viewmodel.DailyWritingMainViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingMainBinding
import com.example.monthlywriting.model.DailyWritingItem
import com.example.monthlywriting.util.CurrentInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyWritingMainFragment : Fragment() {

    private lateinit var binding: FragmentDailyWritingMainBinding
    private val viewModel: DailyWritingMainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyWritingMainBinding.inflate(layoutInflater)

        (activity as MainActivity).setDailyWritingTitle()
//        viewModel.getWritingList(CurrentInfo.currentMonth)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            viewModel.getWritingList(CurrentInfo.currentMonth)
//        }, 2000)
        setViewPager()

        return binding.root
    }

    private fun setViewPager() {
        val adapter = DailyWritingViewPager(requireActivity())
        binding.dailyWritingItem.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        setOnClicks()
//        setObservers()
    }

//    private fun setOnClicks() {
//        binding.apply {
//
//            dailyWritingTextDaily.setOnClickListener {
//                val action = DailyWritingMainFragmentDirections.openAdd("daily")
//                it.findNavController().navigate(action)
//            }
//
//            dailyWritingTextWeekly.setOnClickListener {
//                val action = DailyWritingMainFragmentDirections.openAdd("weekly")
//                it.findNavController().navigate(action)
//            }
//
//            dailyWritingTextMonthly.setOnClickListener {
//                val action = DailyWritingMainFragmentDirections.openAdd("monthly")
//                it.findNavController().navigate(action)
//            }
//
//            dailyWritingItemDaily.apply {
//                setOnClickListener {
//                    val action = DailyWritingMainFragmentDirections.openAdd("daily")
//                    it.findNavController().navigate(action)
//                }
//            }
//
//            dailyWritingItemWeekly.apply {
//                setOnClickListener {
//                    val action = DailyWritingMainFragmentDirections.openAdd("weekly")
//                    it.findNavController().navigate(action)
//                }
//            }
//
//            dailyWritingItemMonthly.apply {
//                setOnClickListener {
//                    val action = DailyWritingMainFragmentDirections.openAdd("monthly")
//                    it.findNavController().navigate(action)
//                }
//            }
//        }
//    }
//
//    private fun setObservers() {
//        viewModel.dailyList.observe(viewLifecycleOwner) {
//
//            if (it.isEmpty()) {
//                binding.dailyWritingItemDaily.visibility = View.GONE
//                binding.dailyWritingTextDaily.visibility = View.VISIBLE
//            } else {
//                binding.dailyWritingItemDaily.visibility = View.VISIBLE
//                binding.dailyWritingTextDaily.visibility = View.GONE
//
//                val mutableList = it.toMutableList()
//                mutableList.add(0, DailyWritingItem(CurrentInfo.currentMonth, "daily" ,getString(R.string.daily)))
//
//                val adapter = DailyWritingItemAdapter(mutableList, "daily")
//                binding.dailyWritingItemDaily.apply {
//                    this.adapter = adapter
//                    layoutManager = LinearLayoutManager(requireContext())
//                }
//            }
//        }
//
//        viewModel.weeklyList.observe(viewLifecycleOwner) {
//            if (it.isEmpty()) {
//                binding.dailyWritingItemWeekly.visibility = View.GONE
//                binding.dailyWritingTextWeekly.visibility = View.VISIBLE
//            } else {
//                binding.dailyWritingItemWeekly.visibility = View.VISIBLE
//                binding.dailyWritingTextWeekly.visibility = View.GONE
//
//                val mutableList = it.toMutableList()
//                mutableList.add(0, DailyWritingItem(CurrentInfo.currentMonth, "weekly", getString(R.string.weekly)))
//
//                val adapter = DailyWritingItemAdapter(mutableList, "weekly")
//                binding.dailyWritingItemWeekly.apply {
//                    this.adapter = adapter
//                    layoutManager = LinearLayoutManager(requireContext())
//                }
//            }
//        }
//
//        viewModel.monthlyList.observe(viewLifecycleOwner) {
//            if (it.isEmpty()) {
//                binding.dailyWritingItemMonthly.visibility = View.GONE
//                binding.dailyWritingTextMonthly.visibility = View.VISIBLE
//            } else {
//                binding.dailyWritingItemMonthly.visibility = View.VISIBLE
//                binding.dailyWritingTextMonthly.visibility = View.GONE
//
//                val mutableList = it.toMutableList()
//                mutableList.add(0, DailyWritingItem(CurrentInfo.currentMonth, "monthly", getString(R.string.monthly)))
//
//                val adapter = DailyWritingItemAdapter(mutableList, "monthly")
//                binding.dailyWritingItemMonthly.apply {
//                    this.adapter = adapter
//                    layoutManager = LinearLayoutManager(requireContext())
//                }
//            }
//        }
//    }
}
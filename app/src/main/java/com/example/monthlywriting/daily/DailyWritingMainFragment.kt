package com.example.monthlywriting.daily

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthlywriting.MainActivity
import com.example.monthlywriting.R
import com.example.monthlywriting.daily.viewmodel.DailyWritingMainViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingMainBinding
import com.example.monthlywriting.model.DailyWritingItem
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DailyWritingMainFragment : Fragment() {

    private lateinit var binding: FragmentDailyWritingMainBinding
    private val viewModel : DailyWritingMainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyWritingMainBinding.inflate(layoutInflater)

        (activity as MainActivity).setDailyWritingTitle()
        getCurrentMonth()
        scrollToTop()

        return binding.root
    }

    private fun getCurrentMonth() {
        val currentMonth = when(SimpleDateFormat("MM", Locale.getDefault()).format(Date(System.currentTimeMillis()))){
            "01" -> resources.getString(R.string.January)
            "02" -> resources.getString(R.string.February)
            "03" -> resources.getString(R.string.March)
            "04" -> resources.getString(R.string.April)
            "05" -> resources.getString(R.string.May)
            "06" -> resources.getString(R.string.June)
            "07" -> resources.getString(R.string.July)
            "08" -> resources.getString(R.string.August)
            "09" -> resources.getString(R.string.September)
            "10" -> resources.getString(R.string.October)
            "11" -> resources.getString(R.string.November)
            "12" -> resources.getString(R.string.December)
            else -> null
        }

        if (currentMonth == null){
            Toast.makeText(requireContext(), "현재 날짜를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.getWritingList(currentMonth)
        }

        //추후 current month 를 app 차원에서 제공하는 방법 생각할 것
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClicks()
        setObservers()
    }

    private fun scrollToTop() {
        binding.dailyFragmentScrollview.post{
            binding.dailyFragmentScrollview.fullScroll(ScrollView.FOCUS_UP)
        }
    }

    private fun setOnClicks() {
        binding.apply {
            dailyWritingTextDaily.setOnClickListener {
                val action = DailyWritingMainFragmentDirections.openAdd("daily")
                it.findNavController().navigate(action)
            }

            dailyWritingTextWeekly.setOnClickListener {
                val action = DailyWritingMainFragmentDirections.openAdd("weekly")
                it.findNavController().navigate(action)
            }

            dailyWritingTextMonthly.setOnClickListener {
                val action = DailyWritingMainFragmentDirections.openAdd("monthly")
                it.findNavController().navigate(action)
            }

            dailyWritingItemDaily.apply {
                setOnClickListener {
                    val action = DailyWritingMainFragmentDirections.openAdd("daily")
                    it.findNavController().navigate(action)
                }
            }

            dailyWritingItemWeekly.apply {
                setOnClickListener {
                    val action = DailyWritingMainFragmentDirections.openAdd("weekly")
                    it.findNavController().navigate(action)
                }
            }

            dailyWritingItemMonthly.apply {
                setOnClickListener {
                    val action = DailyWritingMainFragmentDirections.openAdd("monthly")
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    private fun setObservers() {
        viewModel.dailyList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.dailyWritingItemDaily.visibility = View.GONE
                binding.dailyWritingTextDaily.visibility = View.VISIBLE
            } else {
                binding.dailyWritingItemDaily.visibility = View.VISIBLE
                binding.dailyWritingTextDaily.visibility = View.GONE

                val mutableList = it.toMutableList()
                mutableList.add(0,(DailyWritingItem
                    (-1, "title", "daily", resources.getString(R.string.daily), null, null, mutableListOf())))

                val adapter = DailyWritingItemAdapter(mutableList, "daily")
                binding.dailyWritingItemDaily.apply{
                    this.adapter = adapter
                    layoutManager = LinearLayoutManager(this@DailyWritingMainFragment.context)
                }
            }
        }

        viewModel.weeklyList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.dailyWritingItemWeekly.visibility = View.GONE
                binding.dailyWritingTextWeekly.visibility = View.VISIBLE
            } else {
                binding.dailyWritingItemWeekly.visibility = View.VISIBLE
                binding.dailyWritingTextWeekly.visibility = View.GONE

                val mutableList = it.toMutableList()
                mutableList.add(0,(DailyWritingItem
                    (-1, "title", "weekly", resources.getString(R.string.weekly), null, null, mutableListOf())))

                val adapter = DailyWritingItemAdapter(mutableList, "weekly")
                binding.dailyWritingItemWeekly.apply{
                    this.adapter = adapter
                    layoutManager = LinearLayoutManager(this@DailyWritingMainFragment.context)
                }
            }
        }

        viewModel.monthlyList.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.dailyWritingItemMonthly.visibility = View.GONE
                binding.dailyWritingTextMonthly.visibility = View.VISIBLE
            } else {
                binding.dailyWritingItemMonthly.visibility = View.VISIBLE
                binding.dailyWritingTextMonthly.visibility = View.GONE

                val mutableList = it.toMutableList()
                mutableList.add(0,(DailyWritingItem
                    (-1, "title", "daily", resources.getString(R.string.monthly), null, null, mutableListOf())))

                val adapter = DailyWritingItemAdapter(mutableList, "monthly")
                binding.dailyWritingItemMonthly.apply{
                    this.adapter = adapter
                    layoutManager = LinearLayoutManager(this@DailyWritingMainFragment.context)
                }
            }
        }
    }
}
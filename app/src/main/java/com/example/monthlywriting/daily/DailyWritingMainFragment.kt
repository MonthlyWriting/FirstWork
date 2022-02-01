package com.example.monthlywriting.daily

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.MainActivity
import com.example.monthlywriting.daily.viewmodel.DailyWritingMainViewModel
import com.example.monthlywriting.databinding.FragmentDailyWritingMainBinding
import dagger.hilt.android.AndroidEntryPoint

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
        scrollToTop()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClick()
        setObservers()
        viewModel.getWritingList()
    }

    private fun scrollToTop() {
        binding.dailyFragmentScrollview.post{
            binding.dailyFragmentScrollview.fullScroll(ScrollView.FOCUS_UP)
        }
    }

    private fun setOnClick() {
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
                enableClickListener()
                setOnClickListener {
                    val action = DailyWritingMainFragmentDirections.openAdd("daily")
                    it.findNavController().navigate(action)
                }
            }

            dailyWritingItemWeekly.apply {
                enableClickListener()
                setOnClickListener {
                    val action = DailyWritingMainFragmentDirections.openAdd("weekly")
                    it.findNavController().navigate(action)
                }
            }

            dailyWritingItemMonthly.apply {
                enableClickListener()
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

                val adapter = DailyWritingItemAdapter(it)
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

                val adapter = DailyWritingItemAdapter(it)
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

                val adapter = DailyWritingItemAdapter(it)
                binding.dailyWritingItemMonthly.apply{
                    this.adapter = adapter
                    layoutManager = LinearLayoutManager(this@DailyWritingMainFragment.context)
                }
            }
        }
    }
}

@SuppressLint("ClickableViewAccessibility")
fun RecyclerView.enableClickListener(){
    val gesture = object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            this@enableClickListener.performClick()
            return super.onSingleTapConfirmed(e)
        }
    }
    val detector = GestureDetector(this.context, gesture)
    this.setOnTouchListener { _, event -> detector.onTouchEvent(event) }
}
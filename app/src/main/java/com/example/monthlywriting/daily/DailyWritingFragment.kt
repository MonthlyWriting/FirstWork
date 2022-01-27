package com.example.monthlywriting.daily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthlywriting.R
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

        val dailyAdapter = DailyWritingItemAdapter(emptyList())
        binding.dailyWritingItemDaily.apply{
            adapter = dailyAdapter
            layoutManager = LinearLayoutManager(this@DailyWritingFragment.context)
        }
    }
}
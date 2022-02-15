package com.example.monthlywriting.monthly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.monthlywriting.MainActivity
import com.example.monthlywriting.databinding.FragmentMonthlyWritingBinding

class MonthlyWritingFragment : Fragment() {

    private lateinit var binding: FragmentMonthlyWritingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthlyWritingBinding.inflate(layoutInflater)

        (activity as MainActivity).setMonthlyWritingTitle()

        return binding.root
    }

}
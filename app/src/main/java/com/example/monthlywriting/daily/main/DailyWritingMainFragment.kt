package com.example.monthlywriting.daily.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.FragmentDailyWritingMainBinding
import com.example.monthlywriting.util.CurrentInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyWritingMainFragment : Fragment() {

    private lateinit var binding: FragmentDailyWritingMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyWritingMainBinding.inflate(layoutInflater)

        binding.dailyWritingTitle.text = CurrentInfo.currentMonthName
        setViewPager()

        return binding.root
    }

    private fun setViewPager() {
        val adapter = DailyWritingViewPager(requireActivity())
        binding.dailyWritingItem.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dailyWritingFabAdd.setOnClickListener {
            val action = DailyWritingMainFragmentDirections.openAdd(
                resources.getStringArray(R.array.type)[binding.dailyWritingItem.currentItem]
            )
            it.findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        setViewPager()
    }
}
package com.example.monthlywriting.monthly

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.FragmentMonthlyCardDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthlyCardDetail : Fragment() {

    private lateinit var binding: FragmentMonthlyCardDetailBinding
    private val viewModel: MonthlyCardDetailViewModel by viewModels()

    private val args: MonthlyCardDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthlyCardDetailBinding.inflate(layoutInflater)

        ViewCompat.setTransitionName(binding.monthlyWritingItems, getString(R.string.transition_open_card, args.month))
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)

        setupDisplay()
        setupAdapter()

        return binding.root
    }

    private fun setupAdapter() {
        val adapter = MonthlyWritingItemAdapter(args.month)
        binding.monthlyWritingItems.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.dailyList.observe(viewLifecycleOwner){
            (binding.monthlyWritingItems.adapter as MonthlyWritingItemAdapter).differ.submitList(it)
        }
    }

    private fun setupDisplay() {
        viewModel.getAllItems(args.month)
    }

}
package com.example.monthlywriting.monthly

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.FragmentMonthlyCardDetailBinding

class MonthlyCardDetail : Fragment() {

    private lateinit var binding: FragmentMonthlyCardDetailBinding

    private val args: MonthlyCardDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthlyCardDetailBinding.inflate(layoutInflater)

        binding.linearlayout.transitionName = getString(R.string.transition_open_card, args.month)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.change_bounds)
        Log.d("test",binding.linearlayout.transitionName)

        return binding.root
    }

}
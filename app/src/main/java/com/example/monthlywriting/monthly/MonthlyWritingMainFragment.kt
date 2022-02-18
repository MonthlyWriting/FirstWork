package com.example.monthlywriting.monthly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.FragmentMonthlyWritingBinding
import com.example.monthlywriting.monthly.adapter.MonthlyCardAdapter
import com.example.monthlywriting.util.CurrentInfo.Companion.backgroundList
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MonthlyWritingMainFragment : Fragment() {

    private lateinit var binding: FragmentMonthlyWritingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMonthlyWritingBinding.inflate(layoutInflater)

        setupViewPager()

        return binding.root
    }

    private fun setupViewPager() {

        val transform = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(8))
            addTransformer { view: View, fl: Float ->
                val v = 1 - abs(fl)
                view.scaleY = 0.8f + v * 0.2f
            }
        }

        binding.monthlyCard.apply {
            val adapter = MonthlyCardAdapter(backgroundList) { month, view -> openCard(month, view) }
            this.adapter = adapter
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            setPageTransformer(transform)
        }
    }

    private fun openCard(month: Int, view: View) {
        val extras = FragmentNavigatorExtras(view to getString(R.string.transition_open_card))
        val action = MonthlyWritingMainFragmentDirections.openCard(month)

        NavHostFragment.findNavController(this@MonthlyWritingMainFragment).navigate(action, extras)
    }
}
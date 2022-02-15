package com.example.monthlywriting.monthly

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.FragmentMonthlyWritingBinding
import kotlin.math.abs

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
        val backgroundList = listOf(
            R.drawable.photo_jan,
            R.drawable.photo_feb,
            R.drawable.photo_mar,
            R.drawable.photo_apr,
            R.drawable.photo_may,
            R.drawable.photo_jun,
            R.drawable.photo_jul,
            R.drawable.photo_aug,
            R.drawable.photo_sep,
            R.drawable.photo_oct,
            R.drawable.photo_nov,
            R.drawable.photo_dec,
        )

        val adapter = MonthlyCardAdapter(backgroundList)
        binding.monthlyCard.adapter = adapter
        binding.monthlyCard.offscreenPageLimit = 3
        binding.monthlyCard.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(8))

        transform.addTransformer { view: View, fl: Float ->
            val v = 1 - abs(fl)
            view.scaleY = 0.8f + v * 0.2f
        }

        binding.monthlyCard.setPageTransformer(transform)
    }

}
package com.example.monthlywriting.daily.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DailyWritingViewPager(fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter(fm, lc) {

    private val fragments = mutableListOf(
        DailyWritingItemFragment("daily"),
        DailyWritingItemFragment("weekly"),
        DailyWritingItemFragment("monthly")
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}
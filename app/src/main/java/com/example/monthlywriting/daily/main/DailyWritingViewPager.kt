package com.example.monthlywriting.daily.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DailyWritingViewPager(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private val fragments = mutableListOf(
        DailyWritingItemFragment("daily"),
        DailyWritingItemFragment("weekly"),
        DailyWritingItemFragment("monthly")
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}
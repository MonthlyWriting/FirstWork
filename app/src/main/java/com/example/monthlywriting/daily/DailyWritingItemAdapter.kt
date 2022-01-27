package com.example.monthlywriting.daily

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.FragmentDailyWritingBinding

class DailyWritingItemAdapter(private val list: List<String>) :
    RecyclerView.Adapter<DailyWritingItemAdapter.DailyWritingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWritingItemViewHolder =
        DailyWritingItemViewHolder(FragmentDailyWritingBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: DailyWritingItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    inner class DailyWritingItemViewHolder(val binding: FragmentDailyWritingBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun setData(){
                Log.d("tesT", "came1")
                if (list.isEmpty()){
                    Log.d("tesT", "came2")
                    binding.dailyWritingItemDaily.visibility = View.GONE
                    binding.dailyWritingTextDaily.visibility = View.VISIBLE
                }
            }
    }
}
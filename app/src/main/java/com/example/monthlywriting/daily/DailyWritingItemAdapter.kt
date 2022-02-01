package com.example.monthlywriting.daily

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.DailyWritingItemBinding
import com.example.monthlywriting.model.DailyWritingItem

class DailyWritingItemAdapter(private val list: List<DailyWritingItem>) :
    RecyclerView.Adapter<DailyWritingItemAdapter.DailyWritingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWritingItemViewHolder =
        DailyWritingItemViewHolder(DailyWritingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: DailyWritingItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    inner class DailyWritingItemViewHolder(val binding: DailyWritingItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun setData(){
                binding.dailyWritingItems.text = list[adapterPosition].name
            }
    }
}
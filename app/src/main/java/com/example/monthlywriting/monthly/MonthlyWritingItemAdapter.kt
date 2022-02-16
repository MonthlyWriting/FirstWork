package com.example.monthlywriting.monthly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.MonthlyWritingItemBinding
import com.example.monthlywriting.model.DailyWritingItem

class MonthlyWritingItemAdapter(private val list: List<DailyWritingItem>) :
    RecyclerView.Adapter<MonthlyWritingItemAdapter.MonthlyWritingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyWritingItemViewHolder {
        return MonthlyWritingItemViewHolder(
            MonthlyWritingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MonthlyWritingItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    inner class MonthlyWritingItemViewHolder(private val binding: MonthlyWritingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun setData(){
                binding.monthlyWritingItemName.text = list[adapterPosition].name

                binding.root.setOnClickListener {
                    binding.monthlyWritingExpandedLayout.visibility = View.VISIBLE
                }
            }
    }
}
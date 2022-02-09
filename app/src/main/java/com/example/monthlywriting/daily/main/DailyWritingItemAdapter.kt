package com.example.monthlywriting.daily.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.DailyWritingItemBinding
import com.example.monthlywriting.model.DailyWritingItem

class DailyWritingItemAdapter(
    private val list: List<DailyWritingItem>,
) : RecyclerView.Adapter<DailyWritingItemAdapter.DailyWritingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWritingItemViewHolder =
        DailyWritingItemViewHolder(
            DailyWritingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DailyWritingItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    inner class DailyWritingItemViewHolder(private val binding: DailyWritingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData() {
            binding.dailyWritingItems.text = list[adapterPosition].name
            binding.dailyWritingItems.setOnClickListener {
                val action = DailyWritingMainFragmentDirections.openBottomSheet(list[adapterPosition].id)
                it.findNavController().navigate(action)
            }
        }
    }
}
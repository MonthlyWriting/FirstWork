package com.example.monthlywriting.monthly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.MonthlyCardItemBinding

class MonthlyCardAdapter(private val backgrounds: List<Int>) :
    RecyclerView.Adapter<MonthlyCardAdapter.MonthlyCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyCardViewHolder =
        MonthlyCardViewHolder(MonthlyCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MonthlyCardViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = 12

    inner class MonthlyCardViewHolder(private val binding: MonthlyCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun setData() {
                binding.monthlyCardItem.setBackgroundResource(backgrounds[adapterPosition])
            }
        }

}
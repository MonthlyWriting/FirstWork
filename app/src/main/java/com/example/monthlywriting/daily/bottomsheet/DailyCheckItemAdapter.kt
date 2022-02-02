package com.example.monthlywriting.daily.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.DailyCheckItemBinding
import com.example.monthlywriting.model.DailyCheckItem

class DailyCheckItemAdapter(
    private val list : List<DailyCheckItem>
) : RecyclerView.Adapter<DailyCheckItemAdapter.DailyCheckItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyCheckItemViewHolder =
        DailyCheckItemViewHolder(DailyCheckItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: DailyCheckItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class DailyCheckItemViewHolder (private val binding : DailyCheckItemBinding)
        :RecyclerView.ViewHolder(binding.root) {

        fun setData(){
            binding.dailyCheckDate.text = list[adapterPosition].date
        }
    }
}
package com.example.monthlywriting.daily.main

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.DailyWritingItemBinding
import com.example.monthlywriting.model.DailyWritingItem

class DailyWritingItemAdapter(
    private val list: List<DailyWritingItem>,
    private val type : String,
) : RecyclerView.Adapter<DailyWritingItemAdapter.DailyWritingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWritingItemViewHolder =
        DailyWritingItemViewHolder(DailyWritingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: DailyWritingItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    inner class DailyWritingItemViewHolder(private val binding: DailyWritingItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

            fun setData(){
                binding.dailyWritingItems.apply{
                    when (adapterPosition){
                        0 -> {
                            setTextSize(
                                TypedValue.COMPLEX_UNIT_PX,
                                resources.getDimensionPixelSize(R.dimen.daily_writing_item_title_size).toFloat()
                            )
                            background = null
                            text = list[adapterPosition].name
                            setOnClickListener {
                                val action = DailyWritingMainFragmentDirections.openAdd(type)
                                it.findNavController().navigate(action)
                            }
                        }
                        else -> {
                            text = list[adapterPosition].name
                            setOnClickListener {
                                val action = DailyWritingMainFragmentDirections.openBottomSheet(list[adapterPosition].id)
                                it.findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
    }
}
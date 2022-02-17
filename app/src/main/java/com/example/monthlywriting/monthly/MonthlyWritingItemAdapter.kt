package com.example.monthlywriting.monthly

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.MonthlyWritingItemBinding
import com.example.monthlywriting.model.DailyWritingItem
import com.example.monthlywriting.util.CurrentInfo.Companion.currentYear

class MonthlyWritingItemAdapter(
    private val list: List<DailyWritingItem>,
    private val month: Int,
) : RecyclerView.Adapter<MonthlyWritingItemAdapter.MonthlyWritingItemViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyWritingItemViewHolder {
        context = parent.context
        return MonthlyWritingItemViewHolder(
            MonthlyWritingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MonthlyWritingItemViewHolder, position: Int) {
        holder.setData()

        var isExpanded = false
        holder.itemView.setOnClickListener {
            isExpanded = if(isExpanded){
                holder.close()
                false
            } else {
                holder.open()
                true
            }
        }
    }

    override fun getItemCount(): Int = list.size

    inner class MonthlyWritingItemViewHolder(private val binding: MonthlyWritingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(){
            val item = list[adapterPosition]

            binding.monthlyWritingItemName.text = item.name

            binding.monthlyWritingItemCalendar.apply {
                val adapter = CalendarItemAdapter(list[adapterPosition] ,currentYear, month)
                this.adapter = adapter
                layoutManager = GridLayoutManager(context, 7)
            }

            when (item.type) {
                "daily" -> {
                    binding.monthlyWritingItemType.text = context.resources.getStringArray(R.array.type_cap)[0]
                }
                "weekly"-> {
                    val text = context.resources.getStringArray(R.array.type_cap)[1] +
                            " : " +
                            context.resources.getString(R.string.weekly_times, item.weektimes)
                    binding.monthlyWritingItemType.text = text
                }
                "monthly" -> {
                    if(item.monthtimes == 0){
                        binding.monthlyWritingItemType.text = context.resources.getStringArray(R.array.type_cap)[2]
                    } else {
                        val text = context.resources.getStringArray(R.array.type_cap)[2] +
                                " : " +
                                context.resources.getString(R.string.month_times, item.monthtimesdone.size, item.monthtimes)
                        binding.monthlyWritingItemType.text = text
                    }
                }
            }
        }

        fun close(){
            binding.monthlyWritingItemLayout.visibility = View.GONE
        }

        fun open(){
            binding.monthlyWritingItemLayout.visibility = View.VISIBLE
        }
    }
}
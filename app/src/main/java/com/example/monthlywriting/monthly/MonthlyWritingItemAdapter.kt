package com.example.monthlywriting.monthly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.MonthlyWritingItemBinding
import com.example.monthlywriting.model.DailyWritingItem
import com.example.monthlywriting.util.CurrentInfo.Companion.currentYear
import java.util.*
import kotlin.collections.ArrayList

class MonthlyWritingItemAdapter(
    private val list: List<DailyWritingItem>,
    private val month: Int,
) : RecyclerView.Adapter<MonthlyWritingItemAdapter.MonthlyWritingItemViewHolder>() {

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

        var isExpanded = false

        private val openAnim = TranslateAnimation(
            0f,
            0f,
            0f,
            binding.monthlyWritingExpandedLayout.height.toFloat()
        ).also {
            it.duration = 400
        }

        private val closeAnim = TranslateAnimation(
            0f,
            0f,
            binding.monthlyWritingExpandedLayout.height.toFloat(),
            0f
        ).also {
            it.duration = 400
        }

        fun setData(){
            binding.monthlyWritingItemName.text = list[adapterPosition].name

            binding.root.setOnClickListener {
                if(isExpanded){
                    binding.monthlyWritingExpandedLayout.apply {
                        animation = closeAnim
                        visibility = View.GONE
                    }
                    isExpanded = false
                } else {
                    binding.monthlyWritingExpandedLayout.apply{
                        animation = openAnim
                        visibility = View.VISIBLE
                    }
                    isExpanded = true
                }
            }


            binding.monthlyWritingCalendar.apply {
                val adapter = CalendarItemAdapter(list[adapterPosition] ,currentYear, month)
                this.adapter = adapter
                layoutManager = GridLayoutManager(context, 7)
            }
        }

        private fun getDate(month: Int): ArrayList<Long> {
            val cal = Calendar.getInstance()
            cal.set(currentYear, month - 1, 1, 0 , 0, 0)

            val startTime = cal.timeInMillis

            val endDate = cal.getActualMaximum(Calendar.DATE)
            cal.set(currentYear, month - 1, endDate, 0, 0, 0)

            val endTime = cal.timeInMillis

            return arrayListOf(startTime, endTime)
        }
    }
}
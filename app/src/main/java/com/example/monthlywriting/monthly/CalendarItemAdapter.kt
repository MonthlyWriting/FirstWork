package com.example.monthlywriting.monthly

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.MonthlyWritingCalendarItemBinding
import com.example.monthlywriting.model.DailyWritingItem
import java.util.*

class CalendarItemAdapter(
    private val dailyWritingItem: DailyWritingItem,
    private val year: Int,
    private val month: Int
) : RecyclerView.Adapter<CalendarItemAdapter.CalendarItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarItemViewHolder {
        return CalendarItemViewHolder(
            MonthlyWritingCalendarItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CalendarItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = 42

    inner class CalendarItemViewHolder(private val binding: MonthlyWritingCalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData() {
            val cal = Calendar.getInstance()
            cal.set(year, month - 1, 1, 0,0,0)

            val startIndex = cal.get(Calendar.DAY_OF_WEEK) - 1
            val lastIndex = startIndex + cal.getActualMaximum(Calendar.DATE) - 1

            when (adapterPosition) {
                in startIndex .. lastIndex -> {
                    val date = adapterPosition - startIndex + 1
                    binding.calendarDate.text = date.toString()

                    if(dailyWritingItem.dailymemo.any { it.date == date }){
                        binding.calendarIsMemo.visibility = View.VISIBLE
                        binding.root.setOnClickListener {
                            //open memo
                            val action = MonthlyCardDetailDirections.openDetailMemo(id = dailyWritingItem.id, date = date)
                            it.findNavController().navigate(action)
                        }
                    }

                    when(dailyWritingItem.type){
                        "daily", "weekly" -> {
                            if(dailyWritingItem.done[date - 1]) {
                                binding.calendarDate.setTextColor(Color.BLUE)
                            }
                        }
                        "monthly" -> {
                            for (i in dailyWritingItem.monthtimesdone){
                                if (i == adapterPosition){
                                    binding.calendarDate.setTextColor(Color.BLUE)
                                }
                            }
                        }
                    }
                }
                else -> {
                    binding.calendarDate.text = ""
                }
            }
        }
    }
}
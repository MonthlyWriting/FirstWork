package com.example.monthlywriting.daily.bottomsheet

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.DailyCheckItemBinding
import com.example.monthlywriting.util.CurrentInfo.Companion.currentDate
import com.example.monthlywriting.util.CurrentInfo.Companion.currentMonth

class DailyCheckItemAdapter(
    private val list: MutableList<Boolean>,
    private val setItemDone: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<DailyCheckItemAdapter.DailyCheckItemViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyCheckItemViewHolder {
        context = parent.context
        return DailyCheckItemViewHolder(
            DailyCheckItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyCheckItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class DailyCheckItemViewHolder(private val binding: DailyCheckItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData() {
            binding.dailyCheckDate.text = context.resources.getString(
                R.string.date,
                currentMonth,
                adapterPosition + 1
            )

            if (adapterPosition + 1 == currentDate) {
                binding.dailyCheckIsToday.visibility = View.VISIBLE
            }

            if (list[adapterPosition]) {
                binding.dailyCheckbox.isChecked = true
            }

            binding.dailyCheckbox.setOnCheckedChangeListener { checkbox, isChecked ->
                if (isChecked) {
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage(context.resources.getString(R.string.daily_check_alert))
                        .setPositiveButton(context.resources.getString(R.string.yes)) { _, _ ->
                            setItemDone(adapterPosition, true)
                        }
                        .setNegativeButton(context.resources.getString(R.string.no)) { _, _ ->
                            checkbox.isChecked = false
                        }
                        .show()
                } else if (!isChecked) {
                    setItemDone(adapterPosition, false)
                }
            }
        }
    }
}
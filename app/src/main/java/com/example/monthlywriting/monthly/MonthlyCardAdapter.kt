package com.example.monthlywriting.monthly

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.MonthlyCardItemBinding
import com.example.monthlywriting.util.CurrentInfo.Companion.monthShortNames

class MonthlyCardAdapter(
    private val backgrounds: List<Int>,
    private val openCard: (Int, View) -> Unit
) : RecyclerView.Adapter<MonthlyCardAdapter.MonthlyCardViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyCardViewHolder {
        context = parent.context

        return MonthlyCardViewHolder(
            MonthlyCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MonthlyCardViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = 12

    inner class MonthlyCardViewHolder(val binding: MonthlyCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData() {
            binding.apply {
                monthlyCardTitle.text = monthShortNames[adapterPosition]

                monthlyCardItem.setImageResource(backgrounds[adapterPosition])

                ViewCompat.setTransitionName(
                    monthlyCardItem,
                    context.resources.getString(R.string.transition_open_card, adapterPosition + 1)
                )

                root.setOnClickListener {
                    openCard(adapterPosition + 1, binding.monthlyCardItem)
                }

            }
        }
    }
}
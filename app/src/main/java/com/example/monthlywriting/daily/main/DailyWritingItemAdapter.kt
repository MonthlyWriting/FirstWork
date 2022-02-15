package com.example.monthlywriting.daily.main

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.DailyWritingItemBinding
import com.example.monthlywriting.model.DailyWritingItem

class DailyWritingItemAdapter(
    private val list: List<DailyWritingItem>,
    private val deleteItem: (Int) -> Unit
) : RecyclerView.Adapter<DailyWritingItemAdapter.DailyWritingItemViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWritingItemViewHolder {
        context = parent.context
        return DailyWritingItemViewHolder(
            DailyWritingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: DailyWritingItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    inner class DailyWritingItemViewHolder(private val binding: DailyWritingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData() {
            binding.dailyWritingItems.text = list[adapterPosition].name

            binding.dailyWritingItems.setOnClickListener {
                val action =
                    DailyWritingMainFragmentDirections.openBottomSheet(list[adapterPosition].id)
                it.findNavController().navigate(action)
            }

            binding.dailyWritingItems.setOnLongClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("정말 삭제하시겠습니까?")
                    .setPositiveButton("네") { _, _ ->
                        deleteItem(list[adapterPosition].id)
                    }
                    .setNegativeButton("아니오", null)
                    .show()

                true
            }
        }
    }
}
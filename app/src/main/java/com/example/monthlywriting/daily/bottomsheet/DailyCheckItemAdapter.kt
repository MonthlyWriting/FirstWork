package com.example.monthlywriting.daily.bottomsheet

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.DailyCheckItemBinding

class DailyCheckItemAdapter(
    private val list : MutableList<Boolean>,
    private val context : Context,
    private val setItemDone : (Int, Boolean) -> Unit
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
            binding.dailyCheckDate.text = (adapterPosition + 1).toString()
            if(list[adapterPosition]){
                binding.dailyCheckbox.isChecked = true
            }

            binding.dailyCheckbox.setOnCheckedChangeListener { checkbox, isChecked ->
                if(isChecked){
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("목표를 달성하셨나요?")
                        .setPositiveButton("네") { _, _ ->
                            setItemDone(adapterPosition, true)
                        }
                        .setNegativeButton("아니오") { _, _ ->
                            checkbox.isChecked = false
                        }
                        .show()
                }
                else if (!isChecked){
                    setItemDone(adapterPosition, false)
                }
            }

        }
    }
}
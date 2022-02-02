package com.example.monthlywriting.daily.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.DailyMemoItemBinding
import com.example.monthlywriting.model.DailyMemo

class DailyMemoItemAdapter(
    private val list : List<DailyMemo>
) : RecyclerView.Adapter<DailyMemoItemAdapter.DailyMemoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyMemoItemViewHolder =
        DailyMemoItemViewHolder(DailyMemoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: DailyMemoItemViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class DailyMemoItemViewHolder (private val binding : DailyMemoItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun setData(){
            list[adapterPosition].apply {
                binding.dailyMemoDate.text = date
                binding.dailyMemoContent.text = memo
                if(photo != null) {
                    binding.dailyMemoPhoto.visibility = View.VISIBLE
                } else {
                    binding.dailyMemoPhoto.visibility = View.GONE
                }
            }
        }
    }
}
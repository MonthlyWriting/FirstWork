package com.example.monthlywriting.daily.bottomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.DailyMemoItemBinding
import com.example.monthlywriting.model.DailyMemo

class DailyMemoItemAdapter(
    private val list : MutableList<DailyMemo>,
    private val openDetailMemo : (Int) -> Unit
) : RecyclerView.Adapter<DailyMemoItemAdapter.DailyMemoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyMemoItemViewHolder =
        DailyMemoItemViewHolder(DailyMemoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: DailyMemoItemViewHolder, position: Int) {
        holder.setData()

        holder.itemView.setOnClickListener {
            openDetailMemo(holder.date)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class DailyMemoItemViewHolder (private val binding : DailyMemoItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val date = list[adapterPosition].date

        fun setData(){
            list[adapterPosition].apply {
                binding.dailyMemoDate.text = date.toString()
                binding.dailyMemoContent.text = memo
                if(imgpath != null) {
                    binding.dailyMemoIsPhoto.visibility = View.VISIBLE
                } else {
                    binding.dailyMemoIsPhoto.visibility = View.GONE
                }
            }
        }
    }
}
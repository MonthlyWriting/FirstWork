package com.example.monthlywriting.daily.bottomsheet

import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.App
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.DailyMemoItemBinding
import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.util.CurrentInfo

class DailyMemoItemAdapter(
    private val list : MutableList<DailyMemo>,
    private val openDetailMemo : (Int) -> Unit,
    private val deleteItem : (Int) -> Unit
) : RecyclerView.Adapter<DailyMemoItemAdapter.DailyMemoItemViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyMemoItemViewHolder {
        context = parent.context
        return DailyMemoItemViewHolder(DailyMemoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DailyMemoItemViewHolder, position: Int) {
        holder.setData()

        holder.itemView.setOnClickListener {
            openDetailMemo(holder.getDate())
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class DailyMemoItemViewHolder (private val binding : DailyMemoItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun setData(){
            list[adapterPosition].apply {
                val date = context.resources.getString(R.string.date, CurrentInfo.currentMonth, list[adapterPosition].date)
                binding.dailyMemoDate.text = date

                binding.dailyMemoContent.text = memo

                if(imgpath != null) {
                    binding.dailyMemoIsPhoto.visibility = View.VISIBLE
                } else {
                    binding.dailyMemoIsPhoto.visibility = View.GONE
                }

                binding.dailyMemoDelete.setOnClickListener {
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("$date 메모를 삭제하시겠습니까?")
                        .setPositiveButton("네") { _, _ ->
                            deleteItem(list[adapterPosition].date)
                        }
                        .setNegativeButton("아니오", null)
                        .show()
                }
            }
        }

        fun getDate(): Int {
            return list[adapterPosition].date
        }
    }
}
package com.example.monthlywriting.daily.bottomsheet

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.R
import com.example.monthlywriting.databinding.DailyMemoItemBinding
import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.util.CurrentInfo.Companion.currentMonth

class DailyMemoItemAdapter(
    private val list: MutableList<DailyMemo>,
    private val openDetailMemo: (Int) -> Unit,
    private val deleteItem: (Int) -> Unit
) : RecyclerView.Adapter<DailyMemoItemAdapter.DailyMemoItemViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyMemoItemViewHolder {
        context = parent.context
        return DailyMemoItemViewHolder(
            DailyMemoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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

    inner class DailyMemoItemViewHolder(private val binding: DailyMemoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData() {
            list[adapterPosition].apply {
                val date = context.resources.getString(
                    R.string.date,
                    currentMonth,
                    list[adapterPosition].date
                )
                binding.dailyMemoDate.text = date

                binding.dailyMemoContent.text = memo

                if (imgpath != null) {
                    binding.dailyMemoIsPhoto.visibility = View.VISIBLE
                } else {
                    binding.dailyMemoIsPhoto.visibility = View.GONE
                }

                binding.dailyMemoDelete.setOnClickListener {
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage(context.resources.getString(R.string.daily_memo_alert, date))
                        .setPositiveButton(context.resources.getString(R.string.yes)) { _, _ ->
                            deleteItem(list[adapterPosition].date)
                        }
                        .setNegativeButton(context.resources.getString(R.string.no), null)
                        .show()
                }
            }
        }

        fun getDate(): Int {
            return list[adapterPosition].date
        }
    }
}
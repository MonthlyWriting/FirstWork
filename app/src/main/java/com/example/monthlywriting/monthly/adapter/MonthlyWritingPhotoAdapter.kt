package com.example.monthlywriting.monthly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.monthlywriting.databinding.MonthlyWritingPhotoBinding

class MonthlyWritingPhotoAdapter (private val list: List<String>
): RecyclerView.Adapter<MonthlyWritingPhotoAdapter.MonthlyWritingPhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyWritingPhotoViewHolder {
        return MonthlyWritingPhotoViewHolder(
           MonthlyWritingPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MonthlyWritingPhotoViewHolder, position: Int) {
        holder.setData()
    }

    override fun getItemCount(): Int = list.size + 1


    inner class MonthlyWritingPhotoViewHolder(private val binding: MonthlyWritingPhotoBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun setData(){
                binding.monthlyWritingPhoto
            }
    }
}
package com.example.monthlywriting.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.monthlywriting.model.MonthlyMemo
import com.example.monthlywriting.model.MonthlyWritingItem

@Dao
interface MonthlyWritingItemDao {

    @Query("SELECT * FROM monthly WHERE month = :month")
    fun getAll(month: Int): List<MonthlyWritingItem>

    @Query("UPDATE monthly SET monthlymemo = :monthlyMemo WHERE month = :month")
    fun updateMemo(month: Int, monthlyMemo: MutableList<MonthlyMemo>)

    @Query("UPDATE monthly SET monthlyphoto = :monthlyPhoto WHERE month = :month")
    fun updatePhoto(month: Int, monthlyPhoto: MutableList<String>)

    @Insert
    fun insert(monthlyWritingItem: MonthlyWritingItem)

}
package com.example.monthlywriting.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.monthlywriting.model.DailyWritingItem

@Dao
interface DailyWritingItemDao {
    @Query("SELECT * FROM daily")
    fun selectAll(): List<DailyWritingItem>

    @Query("SELECT * FROM daily WHERE month = :month AND type = :type ")
    fun selectDailyList(month: String, type: String): List<DailyWritingItem>

    @Query("SELECT * FROM daily WHERE id = :id ")
    fun selectSingleItem(id: Int): DailyWritingItem

    @Insert
    fun insert(dailyItem: DailyWritingItem)

    @Query("DELETE FROM daily WHERE id = :id")
    fun delete(id: Int)
}
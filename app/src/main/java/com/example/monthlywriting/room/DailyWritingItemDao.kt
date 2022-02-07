package com.example.monthlywriting.room

import androidx.room.*
import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.model.DailyWritingItem

@Dao
interface DailyWritingItemDao {
    @Query("SELECT * FROM daily")
    fun selectAll(): List<DailyWritingItem>

    @Query("SELECT * FROM daily WHERE month = :month AND type = :type ")
    fun selectDailyList(month: Int, type: String): List<DailyWritingItem>

    @Query("SELECT * FROM daily WHERE id = :id ")
    fun selectSingleItem(id: Int): DailyWritingItem

    @Query("UPDATE daily SET dailymemo = :memo WHERE id = :id")
    fun updateDailyMemo(id: Int, memo : MutableList<DailyMemo>)

    @Query("UPDATE daily SET done = :doneList WHERE id = :id")
    fun updateDone(id: Int, doneList: MutableList<Boolean>)

    @Query("UPDATE daily SET monthtimesdone = :doneList WHERE id = :id")
    fun updateMonthTimesDone(id: Int, doneList: MutableList<Int>)

    @Insert
    fun insert(dailyMemo: DailyWritingItem)

    @Query("DELETE FROM daily WHERE id = :id")
    fun delete(id: Int)

}
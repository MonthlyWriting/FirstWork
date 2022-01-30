package com.example.monthlywriting.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.monthlywriting.model.DailyWritingItem

@Database(
    entities = [DailyWritingItem::class],
    version = 1,
    exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class AppDataBase  : RoomDatabase() {
    abstract fun dailyWritingItemDao() : DailyWritingItemDao
}
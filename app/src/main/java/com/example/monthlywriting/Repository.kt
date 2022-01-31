package com.example.monthlywriting

import com.example.monthlywriting.model.DailyWritingItem
import com.example.monthlywriting.room.DailyWritingItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val dailyWritingItemDao: DailyWritingItemDao) {

    suspend fun insert(newItem : DailyWritingItem) = withContext(Dispatchers.IO) {
        dailyWritingItemDao.insert(newItem)
    }
}
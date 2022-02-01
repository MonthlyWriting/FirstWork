package com.example.monthlywriting

import com.example.monthlywriting.model.DailyWritingItem
import com.example.monthlywriting.room.DailyWritingItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val dailyWritingItemDao: DailyWritingItemDao) {

    suspend fun getAll() : List<DailyWritingItem> = withContext(Dispatchers.IO) {
        return@withContext dailyWritingItemDao.selectAll()
    }

    suspend fun getDailyList(type : String) : List<DailyWritingItem> = withContext(Dispatchers.IO) {
        return@withContext dailyWritingItemDao.selectListByType(type)
    }

    suspend fun insert(newItem : DailyWritingItem) = withContext(Dispatchers.IO) {
        dailyWritingItemDao.insert(newItem)
    }
}
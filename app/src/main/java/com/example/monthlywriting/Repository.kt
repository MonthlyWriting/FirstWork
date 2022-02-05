package com.example.monthlywriting

import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.model.DailyWritingItem
import com.example.monthlywriting.room.DailyWritingItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val dailyWritingItemDao: DailyWritingItemDao) {

    suspend fun getAll() : List<DailyWritingItem> = withContext(Dispatchers.IO) {
        return@withContext dailyWritingItemDao.selectAll()
    }

    suspend fun getDailyList(month: Int, type : String) : List<DailyWritingItem> = withContext(Dispatchers.IO) {
        return@withContext dailyWritingItemDao.selectDailyList(month, type)
    }

    suspend fun getSingleItem(id: Int) : DailyWritingItem = withContext(Dispatchers.IO){
        return@withContext dailyWritingItemDao.selectSingleItem(id)
    }

    suspend fun updateDailyMemo(id: Int, memo : MutableList<DailyMemo>) = withContext(Dispatchers.IO) {
        return@withContext dailyWritingItemDao.updateDailyMemo(id, memo)
    }

    suspend fun insert(newItem : DailyWritingItem) = withContext(Dispatchers.IO) {
        dailyWritingItemDao.insert(newItem)
    }

}
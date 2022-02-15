package com.example.monthlywriting.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.monthlywriting.model.DailyMemo
import com.google.gson.Gson
import java.io.ByteArrayOutputStream

class RoomConverters {

    @TypeConverter
    fun dailyMemoListToJson(list: MutableList<DailyMemo>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToDailyMemoList(json: String): MutableList<DailyMemo> {
        return Gson().fromJson(json, Array<DailyMemo>::class.java).toMutableList()
    }

    @TypeConverter
    fun doneListToJson(list: MutableList<Boolean>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToDoneList(json: String): MutableList<Boolean> {
        return Gson().fromJson(json, Array<Boolean>::class.java).toMutableList()
    }

    @TypeConverter
    fun monthTimesDoneListToJson(list: MutableList<Int>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToMonthTimesDoneList(json: String): MutableList<Int> {
        return Gson().fromJson(json, Array<Int>::class.java).toMutableList()
    }
}
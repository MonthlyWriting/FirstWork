package com.example.monthlywriting.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.monthlywriting.model.DailyMemo
import com.example.monthlywriting.model.MonthlyMemo
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
    fun booleanMutableListToJson(list: MutableList<Boolean>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToBooleanMutableList(json: String): MutableList<Boolean> {
        return Gson().fromJson(json, Array<Boolean>::class.java).toMutableList()
    }

    @TypeConverter
    fun intMutableListToJson(list: MutableList<Int>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToIntMutableList(json: String): MutableList<Int> {
        return Gson().fromJson(json, Array<Int>::class.java).toMutableList()
    }

    @TypeConverter
    fun stringMutableListToJson(list: MutableList<String>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToStringMutableList(json: String): MutableList<String> {
        return Gson().fromJson(json, Array<String>::class.java).toMutableList()
    }

    @TypeConverter
    fun monthlyMemoListToJson(list: MutableList<MonthlyMemo>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToMonthlyMemoList(json: String): MutableList<MonthlyMemo> {
        return Gson().fromJson(json, Array<MonthlyMemo>::class.java).toMutableList()
    }
}
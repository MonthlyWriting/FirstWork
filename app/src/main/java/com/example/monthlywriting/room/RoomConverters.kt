package com.example.monthlywriting.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.monthlywriting.model.DailyMemo
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import kotlin.collections.emptyList as emptyList

class RoomConverters {

    @TypeConverter
    fun dailyMemoListToJson(list: MutableList<DailyMemo>) : String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToDailyMemo(json : String) : MutableList<DailyMemo> {
        return Gson().fromJson(json, Array<DailyMemo>::class.java).toMutableList()
    }


    @TypeConverter
    fun bitmapToByteArray(bitmap: Bitmap) : ByteArray {
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            return toByteArray()
        }
    }

    @TypeConverter
    fun byteArrayToBitmap(byteArray: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
package com.example.monthlywriting.room

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.monthlywriting.model.DailyMemo
import com.google.gson.Gson
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class RoomConverters {

    @TypeConverter
    fun memoToJson(memo : DailyMemo) : String? {
        return Gson().toJson(memo)
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
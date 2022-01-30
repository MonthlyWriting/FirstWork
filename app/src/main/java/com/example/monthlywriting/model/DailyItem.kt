package com.example.monthlywriting.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily")
data class DailyWritingItem(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id") var id: Int,
    @ColumnInfo (name = "month") var month : String,
    @ColumnInfo (name = "type") var type : String,
    @ColumnInfo (name = "name") var name : String,
    @ColumnInfo (name = "dailymemo") var dailyMemo : Map<String, DailyMemo>
)

data class DailyMemo(
    var done : Boolean,
    var memo : String,
    var photo : Bitmap
)
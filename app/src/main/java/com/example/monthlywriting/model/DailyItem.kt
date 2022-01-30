package com.example.monthlywriting.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily")
data class DailyWritingItem(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id") var id: Int,
    @ColumnInfo (name = "month") var month : String,
    @ColumnInfo (name = "type") var type : String,
    @ColumnInfo (name = "name") var name : String,
    @ColumnInfo (name = "days") var days : String?,
    @ColumnInfo (name = "times") var times : String?,
    @Embedded var dailymemo : DailyMemo
)

data class DailyMemo(
    var date : String,
    var done : Boolean,
    var memo : String,
    var photo : Bitmap
)
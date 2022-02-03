package com.example.monthlywriting.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Update
import com.google.gson.annotations.SerializedName

@Entity(tableName = "daily")
data class DailyWritingItem(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id") var id: Int,
    @ColumnInfo (name = "month") var month : String,
    @ColumnInfo (name = "type") var type : String,
    @ColumnInfo (name = "name") var name : String,
    @ColumnInfo (name = "weektimes") var weektimes : Int?,
    @ColumnInfo (name = "monthtimes") var monthtimes : Int?,
    @ColumnInfo (name = "done") var done: MutableList<Boolean>,
    @ColumnInfo (name = "dailymemo") var dailymemo : MutableList<DailyMemo>
)

data class DailyMemo(
    var date : String,
    var memo : String?,
    var photo : Bitmap?
)


package com.example.monthlywriting.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    @ColumnInfo (name = "dailymemo") var dailymemo : MutableList<DailyMemo>
)

data class DailyMemo(
    @SerializedName("date") var date : String,
    @SerializedName("done") var done : Boolean,
    @SerializedName("memo") var memo : String?,
    @SerializedName("photo") var photo : Bitmap?
)
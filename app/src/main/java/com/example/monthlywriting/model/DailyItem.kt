package com.example.monthlywriting.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily")
data class DailyWritingItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "month") var month: Int,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "weektimes") var weektimes: Int?,
    @ColumnInfo(name = "monthtimes") var monthtimes: Int?,
    @ColumnInfo(name = "monthtimesdone") var monthtimesdone: MutableList<Int>,
    @ColumnInfo(name = "done") var done: MutableList<Boolean>,
    @ColumnInfo(name = "dailymemo") var dailymemo: MutableList<DailyMemo>
) {
    constructor(month: Int, type: String, name: String)
            : this(
        0,
        month,
        type,
        name,
        null,
        null,
        mutableListOf(),
        mutableListOf(),
        mutableListOf()
    )
}

data class DailyMemo(
    var date: Int,
    var memo: String,
    var imgpath: String?
)


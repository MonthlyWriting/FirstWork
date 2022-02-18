package com.example.monthlywriting.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly")
data class MonthlyWritingItem(
    @PrimaryKey
    @ColumnInfo(name = "month") var month: Int,
    @ColumnInfo(name = "monthlymemo") var monthlymemo: MutableList<MonthlyMemo>,
    @ColumnInfo(name = "monthlyphoto") var monthlyphoto: MutableList<String>,
)

data class MonthlyMemo(
    var title: String,
    var content: String
)
package com.anureet.expensemanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class expense(
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "amount")
    var amount: Float
)
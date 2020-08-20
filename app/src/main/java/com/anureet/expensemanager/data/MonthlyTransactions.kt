package com.anureet.expensemanager.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize


data class MonthlyTransactions (
    @ColumnInfo(name = "monthYear")
    var monthYear: Long,

    @ColumnInfo(name = "month")
    var month: Int,

    @ColumnInfo(name = "year")
    var year: Int,

    @ColumnInfo(name = "day")
    var day: Int

//    @ColumnInfo(name = "amount")
//    var amount: Double

)

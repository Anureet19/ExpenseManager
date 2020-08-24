package com.anureet.expensemanager.data

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize


data class MonthlyTransactions (
    @ColumnInfo(name = "monthYear")
    var monthYear: Long,

    @ColumnInfo(name = "month")
    var month: Int,

    @ColumnInfo(name = "year")
    var year: Int,

    @ColumnInfo(name = "SUM(amount)")
     var sum:Float

)

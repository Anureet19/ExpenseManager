package com.anureet.expensemanager.data

import android.widget.DatePicker
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*


enum class TransactionType{
    Cash, Credit, Bank
}

@Entity(tableName = "transaction")
data class Transaction (@PrimaryKey(autoGenerate = true) val id: Long,
                        val name: String,
                        val amount: Double,
                        val date: String,
                        val category: String,
                        val transaction_type: String,
                        val comments: String,
                        val month: Int,
                        val year: Int,
                        val day: Int,
                        val datePicker: Date
)
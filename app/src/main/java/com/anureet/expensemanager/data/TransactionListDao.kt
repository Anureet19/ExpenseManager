package com.anureet.expensemanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TransactionListDao{
    @Query("SELECT * FROM `transaction` WHERE date >= date('now') ORDER BY date ASC")
    fun getTransactions(): LiveData<List<Transaction>>

    @Query("SELECT monthYear, month, year, day FROM `transaction` GROUP BY monthYear ORDER BY year, month")
    fun getMonth(): LiveData<List<MonthlyTransactions>>

}
package com.anureet.expensemanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TransactionListDao{
    @Query("SELECT * FROM `transaction` WHERE date >= date('now') ORDER BY date ASC")
    fun getTransactions(): LiveData<List<Transaction>>

    @Query("SELECT month, year, day, SUM(amount) FROM `transaction` GROUP BY year, month ORDER BY year, month, day")
    fun getMonth(): LiveData<List<MonthlyTransactions>>

}
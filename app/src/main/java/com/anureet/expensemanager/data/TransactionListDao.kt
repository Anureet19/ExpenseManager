package com.anureet.expensemanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TransactionListDao{
    @Query("SELECT * FROM `transaction` WHERE date >= date('now') ORDER BY date ASC")
    fun getTransactions(): LiveData<List<Transaction>>

    @Query("SELECT monthYear, month, year, SUM(amount) FROM `transaction` GROUP BY monthYear ORDER BY year, month")
    fun getMonth(): LiveData<List<MonthlyTransactions>>

    @Query("SELECT Sum(amount) FROM `transaction`")
    fun getAmount(): LiveData<Float>

    @Query("SELECT Sum(amount) FROM `transaction` WHERE transaction_type = 'Cash'")
    fun getCashAmount(): LiveData<Float>

    @Query("SELECT Sum(amount) FROM `transaction` WHERE transaction_type = 'Credit'")
    fun getCreditAmount(): LiveData<Float>

    @Query("SELECT Sum(amount) FROM `transaction` WHERE transaction_type = 'Bank'")
    fun getBankAmount(): LiveData<Float>

}
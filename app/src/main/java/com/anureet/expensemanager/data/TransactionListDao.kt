package com.anureet.expensemanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TransactionListDao{
    @Query("SELECT * FROM `transaction` WHERE date >= date('now') ORDER BY date ASC")
    fun getTransactions(): LiveData<List<Transaction>>

    @Query("SELECT t1.monthYear, t1.month, t1.year, SUM(t1.amount) as sum,(SELECT t2.name FROM `transaction` as t2 WHERE t1.monthYear = t2.monthYear LIMIT 3) as expense FROM `transaction` as t1 GROUP BY monthYear ORDER BY year, month")
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
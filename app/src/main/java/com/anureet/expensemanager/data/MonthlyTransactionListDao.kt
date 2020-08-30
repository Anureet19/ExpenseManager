package com.anureet.expensemanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface MonthlyTransactionListDao {
    @Query("SELECT * FROM `transaction` WHERE monthYear = :monthYear ORDER BY date DESC")
    fun getTransactionByMonth(monthYear: Long): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) FROM `transaction` WHERE monthYear = :monthYear")
    fun getSumByMonth(monthYear: Long): LiveData<Float>

    @Query("SELECT name,amount,date FROM `transaction` WHERE date = :date")
    fun getAmountByMonth(date: String): LiveData<List<expense>>

}
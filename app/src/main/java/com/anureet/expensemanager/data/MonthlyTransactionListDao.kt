package com.anureet.expensemanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface MonthlyTransactionListDao {
    @Query("SELECT * FROM `transaction` WHERE monthYear = :monthYear ORDER BY date DESC")
    fun getTransactionByMonth(monthYear: Long): LiveData<List<Transaction>>


}
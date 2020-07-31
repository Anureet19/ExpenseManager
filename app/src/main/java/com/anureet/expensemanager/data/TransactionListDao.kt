package com.anureet.expensemanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface TransactionListDao{
    @Query("SELECT * FROM `transaction`")
    fun getTransactions(): LiveData<List<Transaction>>
}
package com.anureet.expensemanager.data

import android.app.Application
import androidx.lifecycle.LiveData

class MonthlyTransactionListRepository(context: Application) {
    private val monthlyTransactionListDao: MonthlyTransactionListDao = TransactionDatabase.getDatabase(context).monthlyTransactionListDao()

    fun getTransactionByMonth(monthYear: Long): LiveData<List<Transaction>> {
        return monthlyTransactionListDao.getTransactionByMonth(monthYear)
    }
    fun getSumByMonth(monthYear: Long): LiveData<Float>{
        return monthlyTransactionListDao.getSumByMonth(monthYear)
    }
    fun getAmountByMonth(date: String) : LiveData<List<expense>>{
        return monthlyTransactionListDao.getAmountByMonth(date)
    }
}
package com.anureet.expensemanager.data

import android.app.Application
import androidx.lifecycle.LiveData

class MonthlyTransactionListRepository(context: Application) {
    private val monthlyTransactionListDao: MonthlyTransactionListDao = TransactionDatabase.getDatabase(context).monthlyTransactionListDao()

    fun getTransactionByMonth(monthYear: Long): LiveData<List<Transaction>> {
        return monthlyTransactionListDao.getTransactionByMonth(monthYear)
    }
}
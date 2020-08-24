package com.anureet.expensemanager.data

import android.app.Application
import androidx.lifecycle.LiveData

class TransactionListRepository(context: Application) {
    private val transactionListDao: TransactionListDao = TransactionDatabase.getDatabase(context).transactionListDao()

    fun getTransactions(): LiveData<List<Transaction>> {
        return transactionListDao.getTransactions()
    }

    fun getMonth(): LiveData<List<MonthlyTransactions>>{
        return transactionListDao.getMonth()
    }

    fun getAmount(): LiveData<Float>{
        return transactionListDao.getAmount()
    }

    fun getCashAmount(): LiveData<Float>{
        return transactionListDao.getCashAmount()
    }
    fun getBankAmount(): LiveData<Float>{
        return transactionListDao.getBankAmount()
    }
    fun getCreditAmount(): LiveData<Float>{
        return transactionListDao.getCreditAmount()
    }

}
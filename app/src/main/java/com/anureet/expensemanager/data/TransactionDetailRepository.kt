package com.anureet.expensemanager.data

import android.app.Application
import androidx.lifecycle.LiveData

class TransactionDetailRepository(context: Application) {
    private val transactionDetailDao : TransactionDetailDao = TransactionDatabase.getDatabase(context).transactionDetailDao()

    fun getTask(id: Long): LiveData<Transaction> {
        return transactionDetailDao.getTask(id)
    }

    suspend fun insertTask(transaction: Transaction): Long{
        return transactionDetailDao.insertTask(transaction)
    }
    suspend fun updateTask(transaction: Transaction){
        transactionDetailDao.updateTask(transaction)
    }
    suspend fun deleteTask(transaction: Transaction){
        transactionDetailDao.deleteTask(transaction)
    }
}
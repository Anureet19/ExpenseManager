package com.anureet.expensemanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.anureet.expensemanager.data.Transaction
import com.anureet.expensemanager.data.TransactionListRepository

class TransactionListViewModel(application: Application): AndroidViewModel(application) {
    private val repo : TransactionListRepository= TransactionListRepository(application)

    val transactions: LiveData<List<Transaction>>
    get() = repo.getTransactions()
}
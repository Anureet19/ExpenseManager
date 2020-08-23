package com.anureet.expensemanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.anureet.expensemanager.data.MonthlyTransactionListRepository
import com.anureet.expensemanager.data.Transaction
import com.anureet.expensemanager.data.TransactionDetailRepository

class MonthlyTransactionListViewModel(application: Application): AndroidViewModel(application) {
    private val repo: MonthlyTransactionListRepository = MonthlyTransactionListRepository(application)

    private val _transactionMonthYear = MutableLiveData<Long>(0)

//    val transactionMonthYear: LiveData<Long>
//        get() = _transactionMonthYear

    val transactionByMonth: LiveData<List<Transaction>> = Transformations.switchMap(_transactionMonthYear){ id ->
        repo.getTransactionByMonth(id)
    }

    fun setMonthYear(monthYear: Long){
        _transactionMonthYear.value = monthYear
    }

}
package com.anureet.expensemanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.anureet.expensemanager.data.MonthlyTransactionListRepository
import com.anureet.expensemanager.data.Transaction
import com.anureet.expensemanager.data.TransactionDetailRepository
import com.anureet.expensemanager.data.expense

class MonthlyTransactionListViewModel(application: Application): AndroidViewModel(application) {
    private val repo: MonthlyTransactionListRepository = MonthlyTransactionListRepository(application)

    private val _transactionMonthYear = MutableLiveData<Long>(0)
    private val _date = MutableLiveData<String>()

//    val transactionMonthYear: LiveData<Long>
//        get() = _transactionMonthYear

    val transactionByMonth: LiveData<List<Transaction>> = Transformations.switchMap(_transactionMonthYear){ id ->
        repo.getTransactionByMonth(id)
    }
    val sumByMonth: LiveData<Float> = Transformations.switchMap(_transactionMonthYear){ id ->
        repo.getSumByMonth(id)
    }
    val amountByMonth: LiveData<List<expense>> = Transformations.switchMap(_date){ id ->
        repo.getAmountByMonth(id)
    }
//    fun getAmount(date: String): List<expense>{
//        return repo.getAmountByMonth(date)
//    }

    fun setMonthYear(monthYear: Long){
        _transactionMonthYear.value = monthYear
    }
    fun setDate(date: String){
        _date.value = date
    }

}
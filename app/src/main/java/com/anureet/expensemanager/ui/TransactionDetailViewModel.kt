package com.anureet.expensemanager.ui

import android.app.Application
import androidx.lifecycle.*
import com.anureet.expensemanager.data.Transaction
import com.anureet.expensemanager.data.TransactionDetailRepository
import kotlinx.coroutines.launch

class TransactionDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repo: TransactionDetailRepository = TransactionDetailRepository(application)

    private val _transactionId = MutableLiveData<Long>(0)

    val transactionId: LiveData<Long>
        get() = _transactionId

    val transaction: LiveData<Transaction> = Transformations.switchMap(_transactionId){ id ->
        repo.getTask(id)
    }

    fun setTaskId(id: Long){
        if(_transactionId.value != id){
            _transactionId.value = id
        }
    }

    fun saveTask(transaction: Transaction){
        viewModelScope.launch {
            if(_transactionId.value == 0L){
                _transactionId.value = repo.insertTask(transaction)
            }else{
                repo.updateTask(transaction)
            }
        }
    }

    fun deleteTask(){
        viewModelScope.launch {
            transaction.value?.let{
                repo.deleteTask(it)
            }
        }
    }
}
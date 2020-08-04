package com.anureet.expensemanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(DbTypeConverters::class)
@Database(entities = [Transaction::class], version = 1)
abstract class TransactionDatabase: RoomDatabase()  {

    abstract fun transactionDetailDao(): TransactionDetailDao
    abstract fun transactionListDao() : TransactionListDao

    companion object {
        @Volatile
        private var instance: TransactionDatabase? = null

        fun getDatabase(context: Context) = instance
            ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "transaction_database"
                ).build().also { instance = it }
            }
    }
}
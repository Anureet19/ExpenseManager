package com.anureet.expensemanager.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anureet.expensemanager.R
import com.anureet.expensemanager.data.Transaction
import com.anureet.expensemanager.data.Type
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.transaction_date

class TransactionAdapter(private val listener: (Long) -> Unit):
    ListAdapter<Transaction, TransactionAdapter.ViewHolder>(
        DiffCallback()
    ){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init{
            itemView.setOnClickListener{
                listener.invoke(getItem(adapterPosition).id)
            }
        }

        fun bind(transaction: Transaction){
            with(transaction){

                transaction_mode.text = transaction.transaction_type
                transaction_name.text = transaction.name
                if(transaction.income_expense.equals(Type.EXPENSE.toString())) {
                    transaction_amount.text =  ""+ transaction.amount
                    transaction_amount.setTextColor(Color.RED)
                }else{
                    transaction_amount.text = "+" + transaction.amount
                    transaction_amount.setTextColor(Color.GREEN)
                }
                transaction_date.text = ""+transaction.date
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem == newItem
    }
}
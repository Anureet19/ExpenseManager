package com.anureet.expensemanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anureet.expensemanager.R
import com.anureet.expensemanager.data.Transaction
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.month_card.*

class MonthlyCardAdapter(private val listener: (Long) -> Unit):
    ListAdapter<Transaction, MonthlyCardAdapter.ViewHolder>(
        DiffCallback()
    ){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.month_card, parent, false)

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
                month_name.text = transaction.month
                budget_exceeded.text = "not exceeded"
            }
        }
    }
}

//class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
//    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
//        return oldItem == newItem
//    }
//}
package com.anureet.expensemanager.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anureet.expensemanager.R
import com.anureet.expensemanager.data.MonthlyTransactions
import com.anureet.expensemanager.data.Transaction
import com.anureet.expensemanager.selectMonth
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.*
import kotlinx.android.synthetic.main.list_item.transaction_date
import kotlinx.android.synthetic.main.month_card.*

class MonthlyCardAdapter(private val listener: (Long) -> Unit):
    ListAdapter<MonthlyTransactions, MonthlyCardAdapter.ViewHolder>(
        DiffCallback2()
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
                listener.invoke(getItem(adapterPosition).monthYear)
            }
        }

        fun bind(monthlyTransactions: MonthlyTransactions){
            with(monthlyTransactions){
                month_name.text = selectMonth(monthlyTransactions.month)
                budget_exceeded.text = "Not exceeded"

            }
        }
    }
}
class DiffCallback2 : DiffUtil.ItemCallback<MonthlyTransactions>() {
    override fun areItemsTheSame(oldItem: MonthlyTransactions, newItem: MonthlyTransactions): Boolean {
        return oldItem.monthYear == newItem.monthYear
    }

    override fun areContentsTheSame(oldItem: MonthlyTransactions, newItem: MonthlyTransactions): Boolean {
        return oldItem == newItem
    }
}
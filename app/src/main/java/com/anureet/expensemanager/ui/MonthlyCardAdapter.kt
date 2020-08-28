package com.anureet.expensemanager.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anureet.expensemanager.R
import com.anureet.expensemanager.data.MonthlyTransactions
import com.anureet.expensemanager.selectMonth
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.month_card.*
import kotlinx.android.synthetic.main.month_card.view.*
import java.nio.file.Files.size

class MonthlyCardAdapter(private val listener: (Long) -> Unit, val context: Context):
    ListAdapter<MonthlyTransactions, MonthlyCardAdapter.ViewHolder>(
        DiffCallback2()
    ){

    private var viewPool : RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.month_card, parent, false)

        return ViewHolder(itemLayout,context)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder (override val containerView: View,val context: Context) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init{
            itemView.setOnClickListener{
                listener.invoke(getItem(adapterPosition).monthYear)
            }

        }


    @SuppressLint("WrongConstant")
    fun bind(monthlyTransactions: MonthlyTransactions){
            val sharedPreferences : SharedPreferences = this.context.getSharedPreferences("Preference", Context.MODE_PRIVATE)
            var monthlyBudget = sharedPreferences.getFloat("Budget",0f)
            with(monthlyTransactions) {
                month_name.text = selectMonth(monthlyTransactions.month)
                year_name.text = " " + monthlyTransactions.year.toString()
                Log.d("MonthlyCard", "aug: " + monthlyTransactions.sum + " " + monthlyBudget)
                if ((monthlyTransactions.sum * (-1)) > monthlyBudget) {
                    budget_exceeded.text = "Budget Exceeded"
                    budget_exceeded.error = "Budget Exceeded"
                } else {
                    budget_exceeded.text = ""
                    budget_exceeded.error = null
                }

                val childLayoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        childLayoutManager.initialPrefetchItemCount = 4
                monthcard_list.apply {
                    layoutManager = childLayoutManager
                    adapter = MonthCardAdapter(monthlyTransactions.children)
                    setRecycledViewPool(viewPool)
                }

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

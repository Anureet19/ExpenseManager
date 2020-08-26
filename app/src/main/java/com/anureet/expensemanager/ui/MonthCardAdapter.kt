package com.anureet.expensemanager.ui

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anureet.expensemanager.R
import com.anureet.expensemanager.data.MonthlyTransactions
import com.anureet.expensemanager.data.expense
import com.anureet.expensemanager.selectMonth
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cardlist_item.*
import kotlinx.android.synthetic.main.cardlist_item.view.*
import kotlinx.android.synthetic.main.month_card.*

//class MonthCardAdapter (private val children: List<expense>,val context: Context):
//    ListAdapter<expense, MonthCardAdapter.ViewHolder>(
//        DiffCallback3()
//    ){
//
//
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): ViewHolder {
//        val itemLayout = LayoutInflater.from(parent.context)
//            .inflate(R.layout.cardlist_item, parent, false)
//
//
//        return ViewHolder(itemLayout)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    inner class ViewHolder (override val containerView: View) : RecyclerView.ViewHolder(containerView),
//        LayoutContainer {
//
//        fun bind(expenses : expense){
//            with(expenses){
//                item_name.text = expenses.name
//                item_price.text = expenses.amount.toString()
//            }
//        }
//    }
//}
//class DiffCallback3 : DiffUtil.ItemCallback<expense>() {
//    override fun areItemsTheSame(oldItem: expense, newItem: expense): Boolean {
//        return oldItem.date == newItem.date
//    }
//
//    override fun areContentsTheSame(oldItem: expense, newItem: expense): Boolean {
//        return oldItem == newItem
//    }
//
//}

class MonthCardAdapter(private val children : List<expense>)
    : RecyclerView.Adapter<MonthCardAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        val v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.cardlist_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return children.size
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        val child = children[position]
        holder.itemName.text = child.name
        holder.itemAmount.text = child.amount.toString()
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val itemName : TextView = itemView.item_name
        val itemAmount: TextView = itemView.item_price

    }
}


package com.anureet.expensemanager.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anureet.expensemanager.R
import com.anureet.expensemanager.data.expense
import kotlinx.android.synthetic.main.cardlist_item.view.*



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
        if(child.amount<0) {
            holder.itemAmount.text = child.amount.toString()
            holder.itemAmount.setTextColor(Color.RED)
        }else{
            holder.itemAmount.text = "+"+child.amount.toString()
            holder.itemAmount.setTextColor(Color.GREEN)
        }
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val itemName : TextView = itemView.item_name
        val itemAmount: TextView = itemView.item_price

    }
}


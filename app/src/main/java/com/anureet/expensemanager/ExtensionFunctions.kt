package com.anureet.expensemanager

import java.text.SimpleDateFormat
import java.util.*

fun Date.readableFormat(): String{
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return dateFormat.format(this)
}

fun selectMonth(month: String): String{
    when (month) {
        "01" -> return "January"
        "02" -> return "February"
        "03" -> return "March"
        "04" -> return "April"
        "05" -> return "May"
        "06" -> return "June"
        "07" -> return "July"
        "08" -> return "August"
        "09" -> return "September"
        "10" -> return "October"
        "11" -> return "November"
        "12" -> return "December"
        else -> return "Invalid"
    }
}
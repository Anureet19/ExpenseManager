package com.anureet.expensemanager.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anureet.expensemanager.MaterialSpinnerAdapter
import com.anureet.expensemanager.R
import com.anureet.expensemanager.data.Type
import com.anureet.expensemanager.data.Transaction
import com.anureet.expensemanager.data.TransactionMode
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import java.text.SimpleDateFormat
import java.util.*


class AddTransactionFragment : Fragment() {

    private lateinit var viewModel: TransactionDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TransactionDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Implementation of DatePicker to set valid dates
        transaction_date_layout.editText?.transformIntoDatePicker(requireContext(), "dd/MM/yyyy")
        transaction_date_layout.editText?.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", Date())

        recurring_from_date.editText?.transformIntoDatePicker(requireContext(), "dd/MM/yyyy")
        recurring_from_date.editText?.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", Date())

        recurring_to_date.editText?.transformIntoDatePicker(requireContext(), "dd/MM/yyyy")
        recurring_to_date.editText?.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", Date())

        // Setting up drop down
        val type = mutableListOf<String>()
        TransactionMode.values().forEach { type.add(it.name) }
        val adapter = MaterialSpinnerAdapter(requireActivity(), R.layout.spinner_item, type)
        (transaction_type_spinner_layout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val id = AddTransactionFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setTaskId(id)
        if(!(id == 0L)){
            disableFields()
            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit -> {
                        // Handle edit icon press
                        enableFields()
                        true
                    }
                    R.id.delete -> {
                        // Handle delete icon press
                        deleteTransaction()
                        true
                    }
                    else -> false
                }
            }
        }


        viewModel.transaction.observe(viewLifecycleOwner, Observer {
            it?.let{ setData(it) }
        })
        expense_button.setOnClickListener {
            saveTask(Type.EXPENSE)
        }
        income_button.setOnClickListener {
            saveTask(Type.INCOME)
        }
    }

    private fun enableFields() {
        transaction_name.isEnabled = true
        transaction_amount_add.isEnabled = true
        transaction_date_layout.isEnabled = true
        recurring_to_date.isEnabled = true
        recurring_from_date.isEnabled = true
        recurring_transaction.isEnabled = true
        category_spinner_layout.isEnabled = true
        transaction_type_spinner_layout.isEnabled = true
        comments.isEnabled = true
        expense_button.isEnabled = true
        income_button.isEnabled = true
    }

    private fun disableFields() {
        transaction_name.isEnabled = false
        transaction_amount_add.isEnabled = false
        transaction_date_layout.isEnabled = false
        recurring_to_date.isEnabled = false
        recurring_from_date.isEnabled = false
        recurring_transaction.isEnabled = false
        category_spinner_layout.isEnabled = false
        transaction_type_spinner_layout.isEnabled = false
        comments.isEnabled = false
        expense_button.isEnabled = false
        income_button.isEnabled = false

    }

    // Setting up data
    private fun setData(transaction: Transaction){
        transaction_name.editText?.setText(transaction.name)
        transaction_amount_add.editText?.setText((transaction.amount *(-1)).toString())

        var date = transaction.day.toString() +"/0"+transaction.month+"/"+transaction.year
        if(transaction.month<10)
            date = transaction.day.toString() +"/0"+transaction.month+"/"+transaction.year

        transaction_date_layout.editText?.setText(date)
        transaction_type_spinner_layout.editText?.setText(transaction.transaction_type)
        category_spinner_layout.editText?.setText(transaction.category)
        comments.editText?.setText(transaction.comments)
    }

    // Saving task
    private fun <E : Enum<E>> saveTask(mode: E){
        val name = transaction_name.editText?.text.toString()
        val amount = transaction_amount_add.editText?.text.toString()
        val category = category_spinner_layout.editText?.text.toString()

        //Converting amount to float
        var finalAmt = amount.toFloat()
        if(mode == Type.EXPENSE){
            finalAmt *= -1
        }

        var date = transaction_date_layout.editText?.text.toString()

        // Storing date as day,month and year
        val month = Integer.parseInt(date.substring(3,5))
        val year = Integer.parseInt(date.substring(6))
        val day = Integer.parseInt(date.substring(0,2))

        if(month<10)
            date = ""+year+"-0"+month+"-"+day
        else
            date = ""+year+"-"+month+"-"+day

        val datePicker: Date = Date(year,month,day)
        Log.d("Add Transaction","date: "+datePicker)
        val monthYear = (""+month+year).toLong()

        val type = transaction_type_spinner_layout.editText?.text.toString()
        val comments = comments.editText?.text.toString()

//        updateNetBalance(mode,amount)

        val transaction = Transaction(viewModel.transactionId.value!!, name, finalAmt, date,category, type, comments, month, year, day, datePicker,monthYear,mode.toString())
        viewModel.saveTask(transaction)

        activity!!.onBackPressed()
    }

    // Updating net balance
//    fun <E : Enum<E>> updateNetBalance(mode: E, amount: String){
//        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE)
//        var monthlyBudget = sharedPreferences.getFloat("Budget",0f)
//
//        if(mode == Type.EXPENSE){
//            monthlyBudget = (monthlyBudget - amount.toDouble()).toFloat()
//        }else{
//            monthlyBudget = (monthlyBudget + amount.toDouble()).toFloat()
//        }
//
//
//        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
//        editor.putFloat(getString(R.string.netBalance), monthlyBudget)
//        editor.apply()
//
//    }


    // Setting up Calendar for DatePicker
    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
//                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

    fun deleteTransaction() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Alert!")

        builder.setMessage("Do you want to delete item?")

        builder.setPositiveButton("delete") { dialogInterface, which ->
            viewModel.deleteTask()
            requireActivity().onBackPressed()
        }
        builder.setNegativeButton("cancel") { dialogInterface, which ->

        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}


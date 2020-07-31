package com.anureet.expensemanager.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anureet.expensemanager.MaterialSpinnerAdapter
import com.anureet.expensemanager.R
import com.anureet.expensemanager.data.Transaction
import com.anureet.expensemanager.data.TransactionType
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import kotlinx.android.synthetic.main.fragment_add_transaction.transaction_name
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
        transaction_date_layout.editText?.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
        transaction_date_layout.editText?.transformIntoDatePicker(requireContext(), "MM/dd/yyyy", Date())

        recurring_from_date.editText?.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
        recurring_from_date.editText?.transformIntoDatePicker(requireContext(), "MM/dd/yyyy", Date())

        recurring_to_date.editText?.transformIntoDatePicker(requireContext(), "MM/dd/yyyy")
        recurring_to_date.editText?.transformIntoDatePicker(requireContext(), "MM/dd/yyyy", Date())

        // Setting up drop down
        val type = mutableListOf<String>()
        TransactionType.values().forEach { type.add(it.name) }
        val adapter = MaterialSpinnerAdapter(requireActivity(), R.layout.spinner_item, type)
        (transaction_type_spinner_layout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val id = AddTransactionFragmentArgs.fromBundle(
            requireArguments()
        ).id

        viewModel.setTaskId(id)

        viewModel.transaction.observe(viewLifecycleOwner, Observer {
            it?.let{ setData(it) }
        })
        expense_button.setOnClickListener {
            saveTask()
        }
        income_button.setOnClickListener {
            saveTask()
        }
    }

    // Setting up data
    private fun setData(transaction: Transaction){
        transaction_name.editText?.setText(transaction.name)
        transaction_amount_add.editText?.setText(transaction.amount.toString())
        transaction_date_layout.editText?.setText(transaction.date)
        transaction_type_spinner_layout.editText?.setText(transaction.transaction_type)
        category_spinner_layout.editText?.setText(transaction.category)
        comments.editText?.setText(transaction.comments)
    }

    // Saving task
    private fun saveTask(){
        val name = transaction_name.editText?.text.toString()
        val amount = transaction_amount_add.editText?.text.toString()
        val category = category_spinner_layout.editText?.text.toString()
        val date = transaction_date_layout.editText?.text.toString()
        val type = transaction_type_spinner_layout.editText?.text.toString()
        val comments = comments.editText?.text.toString()

        val transaction = Transaction(viewModel.transactionId.value!!, name, Integer.parseInt(amount).toDouble(), date,category, type, comments)
        viewModel.saveTask(transaction)

        requireActivity().onBackPressed()
    }


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
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

}



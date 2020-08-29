package com.anureet.expensemanager.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anureet.expensemanager.R
import kotlinx.android.synthetic.main.fragment_monthly_expense.*


class MonthlyExpenseFragment : Fragment() {
    private lateinit var viewModel: MonthlyTransactionListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MonthlyTransactionListViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly_expense, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setMonthlyBalance()

        val monthYear = MonthlyExpenseFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setMonthYear(monthYear)

        // Transaction List
        with(monthly_transaction_list){
            layoutManager = LinearLayoutManager(activity)
            adapter = TransactionAdapter {
                findNavController().navigate(
                    MonthlyExpenseFragmentDirections.actionMonthlyExpenseFragmentToAddTransactionFragment(
                        it
                    )
                )
            }
        }

        viewModel.transactionByMonth.observe(viewLifecycleOwner, Observer{
            (monthly_transaction_list.adapter as TransactionAdapter).submitList(it)
        })

        add_transaction.setOnClickListener {
            findNavController().navigate(MonthlyExpenseFragmentDirections.actionMonthlyExpenseFragmentToAddTransactionFragment(0))
        }


    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun setMonthlyBalance(){
        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE)
        var monthlyBalance = sharedPreferences.getFloat(getString(R.string.FinalMonthBudget),0f)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()

        viewModel.sumByMonth.observe(viewLifecycleOwner, Observer {
            var monthBalance = sharedPreferences.getFloat(getString(R.string.FinalMonthBudget),0f)
            if(it!=null) {
                monthBalance += it
                month_balance.text = monthBalance.toString()
                updateProgressBar(sharedPreferences, monthBalance)
                amount_saved.text = (monthBalance).toString()
                amount_spent.text = (it*-1).toString()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateProgressBar(sharedPreferences: SharedPreferences, balance: Float) {
        var monthBalance = sharedPreferences.getFloat(getString(R.string.FinalMonthBudget),0f)

        var progress = (balance/monthBalance)*100

        if(progress>100){
            pb.progress = 100
        }else {
            pb.progress = progress.toInt()
        }

    }


}
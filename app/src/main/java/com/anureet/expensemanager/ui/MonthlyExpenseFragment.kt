package com.anureet.expensemanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.anureet.expensemanager.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.add_transaction
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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


    }


}
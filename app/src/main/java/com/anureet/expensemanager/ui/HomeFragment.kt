package com.anureet.expensemanager.ui

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anureet.expensemanager.R
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.lifecycle.Observer
import com.anureet.expensemanager.data.Transaction
import org.eazegraph.lib.models.PieModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: TransactionListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TransactionListViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Getting data to set up name and monthly budget in the home screen
        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE)
        var monthlyBudget = sharedPreferences.getFloat("Budget",0f)

        net_balance.text = monthlyBudget.toString()

        // Adding pie chart
        piechart.addPieSlice(
            PieModel("Cash", 50F, Color.parseColor("#FFA726"))
        )
        piechart.addPieSlice(
            PieModel("Credit", 25F, Color.parseColor("#66BB6A"))
        )
        piechart.addPieSlice(
            PieModel("Bank", 25F, Color.parseColor("#EF5350"))
        )
        piechart.startAnimation();

        // Transaction List
        with(transaction_list){
            layoutManager = LinearLayoutManager(activity)
            adapter = TransactionAdapter {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToAddTransactionFragment(
                        it
                    )
                )
            }
        }

        add_transaction.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAddTransactionFragment(
                    0
                )
            )
        }

        // Month Card List
//        with(monthly_card_list){
//            layoutManager = LinearLayoutManager(activity)
//            adapter = MonthlyCardAdapter {
//                findNavController().navigate(
//                    HomeFragmentDirections.actionHomeFragmentToMonthlyExpenseFragment(
//                        it
//                    )
//                )
//            }
//
//        }

//        viewModel.month.observe(viewLifecycleOwner, Observer{
//            Log.d("Main Activity","list :"+ it)
//
//
//        })


        viewModel.transactions.observe(viewLifecycleOwner, Observer{
            (transaction_list.adapter as TransactionAdapter).submitList(it)
//            (monthly_card_list.adapter as MonthlyCardAdapter).submitList(it)

        })


    }


}
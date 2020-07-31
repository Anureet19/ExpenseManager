package com.anureet.expensemanager.ui

import android.os.Bundle
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

        viewModel.transactions.observe(viewLifecycleOwner, Observer{
            (transaction_list.adapter as TransactionAdapter).submitList(it)
        })
    }


}
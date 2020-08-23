package com.anureet.expensemanager.ui

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anureet.expensemanager.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.set_balance_info.view.*
import org.eazegraph.lib.charts.PieChart
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
        var yearlyBudget = sharedPreferences.getFloat("Budget",0f)*12

        net_balance.text = yearlyBudget.toString()

        updatePieChart()

        set_balance_info.setOnClickListener {
            showDialog()
        }

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
        with(monthly_card_list){
            layoutManager = LinearLayoutManager(activity)
            adapter = MonthlyCardAdapter {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToMonthlyExpenseFragment(
                        it
                    )
                )
            }

        }

        viewModel.month.observe(viewLifecycleOwner, Observer{
            (monthly_card_list.adapter as MonthlyCardAdapter).submitList(it)
        })


        viewModel.transactions.observe(viewLifecycleOwner, Observer{
            (transaction_list.adapter as TransactionAdapter).submitList(it)
        })


    }
    private fun showDialog() {

        val dialog = LayoutInflater.from(requireContext()).inflate(R.layout.set_balance_info, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(dialog)
            .setTitle("Set Details")
        //show dialog
        val  mAlertDialog = mBuilder.show()

        checkValues(dialog)

        dialog.set_info.setOnClickListener {
            mAlertDialog.dismiss()
            val cashAmount = dialog.Cash.text.toString()
            val bankAmount = dialog.Bank.text.toString()
            setBalanceInfo(cashAmount.toFloat(),bankAmount.toFloat(),dialog)
        }
        dialog.cancel.setOnClickListener { mAlertDialog.dismiss() }
        mAlertDialog.show()

    }

    private fun checkValues(dialog: View) {
        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE)

        var monthlyBudget = sharedPreferences.getFloat("Budget",0f)
        var yearlyBudget = monthlyBudget*12


        val boardingTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){
                val bankAmount = dialog.Bank.text.toString()
                val cashAmount = dialog.Cash.text.toString()

                if(cashAmount.isEmpty() || bankAmount.isEmpty()){
                    dialog.set_info.isEnabled = false
                }else if(!cashAmount.isEmpty() && cashAmount.toFloat() > yearlyBudget){
                    dialog.set_info.isEnabled = false
                    dialog.Cash.setError("Greater than net balance available")
                }else if(!bankAmount.isEmpty() && bankAmount.toFloat()>yearlyBudget || cashAmount.toFloat()+bankAmount.toFloat() > yearlyBudget){
                    dialog.set_info.isEnabled = false
                    dialog.Bank.setError("Greater than net balance available")
                }
                else{
                    dialog.Cash.error = null
                    dialog.set_info.isEnabled = true
                    val creditAmount = yearlyBudget - (cashAmount.toFloat() + bankAmount.toFloat())
                    dialog.Credit.setText(creditAmount.toString())
                }

            }
        }
        dialog.Cash.addTextChangedListener(boardingTextWatcher)
        dialog.Bank.addTextChangedListener(boardingTextWatcher)

    }


    private fun setBalanceInfo(cashAmount: Float, bankAmount: Float, dialog: View) {
        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE)

        var monthlyBudget = sharedPreferences.getFloat("Budget",0f)
        val creditAmount = (monthlyBudget*12) - (cashAmount + bankAmount)
        dialog.Credit.setText(creditAmount.toString())

        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putFloat("CashAmount",cashAmount)
        editor.putFloat("BankAmount",bankAmount)
        editor.putFloat("CreditAmount",creditAmount)
        editor.apply()

        //UpdatePieChart
        updatePieChart()

    }

    fun updatePieChart(){

        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE)
        var mcash = sharedPreferences.getFloat("CashAmount",0f)
        var mbank = sharedPreferences.getFloat("BankAmount",0f)
        var mcredit = sharedPreferences.getFloat("CreditAmount",0f)

        cash_amount.text = ""+mcash
        debit_amount.text = ""+mbank
        credit_amount.text = ""+mcredit

        var cash = (mcash / (mcash+mbank+mcredit))*100
        var credit = (mcredit / (mcash+mbank+mcredit))*100
        var bank = (mbank / (mcash+mbank+mcredit))*100

        piechart.clearChart()
        // Adding pie chart
        piechart?.addPieSlice(
            PieModel("Cash", cash, Color.parseColor("#FFA726"))
        )
        piechart?.addPieSlice(
            PieModel("Credit", credit, Color.parseColor("#66BB6A"))
        )
        piechart?.addPieSlice(
            PieModel("Bank", bank, Color.parseColor("#EF5350"))
        )

        piechart?.startAnimation();
    }


}
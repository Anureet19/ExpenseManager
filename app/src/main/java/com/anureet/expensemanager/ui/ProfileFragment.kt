package com.anureet.expensemanager.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anureet.expensemanager.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Adding back button
        val toolbar : MaterialToolbar = requireActivity().findViewById(R.id.profileTopAppBar)
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("Preference", Context.MODE_PRIVATE)
        var monthlyBudget = sharedPreferences.getFloat(getString(R.string.FinalMonthBudget),0f)
        var name = sharedPreferences.getString(getString(R.string.Name),null)
        var income = sharedPreferences.getFloat(getString(R.string.Income),0f)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()

        disableFields()
        if(name!=null){
            setValues(name,monthlyBudget,income)
        }
        appBar()


//        saveButton.isEnabled = !(newName=="" || newMonthlyBudget=="")

        saveButton.setOnClickListener {
            var newName = profile_name.editText?.text.toString()
            var newMonthlyBudget = monthly_budget.editText?.text.toString()
            var newIncome = monthly_income.editText?.text.toString()
            if(!newMonthlyBudget.equals(monthlyBudget)) {
                if (newName != "" && newMonthlyBudget != "")
                    updateDetails(editor, newName, newMonthlyBudget, newIncome)
            }
            requireActivity().onBackPressed()

        }

    }

    private fun setValues(name: String, monthlyBudget: Float, income: Float) {
        profile_name.editText?.setText(name)
        monthly_budget.editText?.setText(monthlyBudget.toString())
        if(income!=0f)
            monthly_income.editText?.setText(income.toString())
    }

    private fun appBar() {
        profileTopAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.editProfile -> {
                    enableFields()
                    true
                }
                else -> false
            }
        }
    }

    private fun updateDetails(editor: SharedPreferences.Editor, newName: String, newMonthlyBudget: String, newIncome: String) {
        editor.putString(getString(R.string.Name),newName)
        editor.putFloat(getString(R.string.FinalMonthBudget),newMonthlyBudget.toFloat())
        editor.putFloat(getString(R.string.netBalance),newMonthlyBudget.toFloat())
        editor.putFloat(getString(R.string.YearlyBudget),newMonthlyBudget.toFloat()*12)
        if(newIncome!="")
            editor.putFloat(getString(R.string.Income),newIncome.toFloat())

        // reset cash,credit,debit info
        editor.putFloat(getString(R.string.CASH),0f)
        editor.putFloat(getString(R.string.CREDIT),0f)
        editor.putFloat(getString(R.string.BANK),0f)
        editor.putBoolean(getString(R.string.FLAG),false)

        editor.commit()
    }

    private fun enableFields() {
        profile_name.isEnabled = true
        monthly_budget.isEnabled = true
        monthly_income.isEnabled = true
        saveButton.isEnabled = true
    }

    private fun disableFields() {
        profile_name.isEnabled = false
        monthly_budget.isEnabled = false
        monthly_income.isEnabled = false
        saveButton.isEnabled = false
    }


}
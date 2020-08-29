package com.anureet.expensemanager.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anureet.expensemanager.R
import kotlinx.android.synthetic.main.fragment_onboarding.*


class OnboardingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences : SharedPreferences = requireActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        var openedFirstTime: String? = sharedPreferences.getString("FirstTimeInstall","")
        if(openedFirstTime.equals("Yes")){
            findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment())
        }else{

            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("FirstTimeInstall","Yes")
            editor.apply()

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        profile_name.editText?.addTextChangedListener(boardingTextWatcher)
        monthly_budget.editText?.addTextChangedListener(boardingTextWatcher)
        monthly_income.editText?.addTextChangedListener(boardingTextWatcher)

        val sharedPreferences : SharedPreferences = this.requireActivity().getSharedPreferences("Preference",Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()

        val income = monthly_income.editText?.text.toString()
        if(income!=""){
            editor.putFloat(getString(R.string.Income),income.toFloat())
        }

        continueButton.setOnClickListener {
            val name = profile_name.editText?.text.toString()
            val monthlyBudget = monthly_budget.editText?.text.toString()

            editor.putString(getString(R.string.Name),name)
            editor.putFloat(getString(R.string.netBalance),monthlyBudget.toFloat())
            editor.putFloat(getString(R.string.YearlyBudget),monthlyBudget.toFloat()*12)
            editor.putFloat(getString(R.string.FinalMonthBudget),monthlyBudget.toFloat())
            editor.apply()
            findNavController().navigate(
                OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment(name,monthlyBudget.toFloat())
            )
        }

    }

    private val boardingTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val name = profile_name.editText?.text.toString()
            val monthlyBudget = monthly_budget.editText?.text.toString()

//            continueButton.isEnabled = !name.isEmpty() && !monthlyBudget.isEmpty()

            if(name.isEmpty()){
                continueButton.isEnabled = false
                profile_name.error = "This field cannot be empty"
                profile_name.isEndIconVisible = true

            }
            if(monthlyBudget.isEmpty()){
                continueButton.isEnabled = false
                monthly_budget.error = "This field cannot be empty"
                monthly_budget.isEndIconVisible = true
            }
            else{
                continueButton.isEnabled = true
                monthly_budget.isEndIconVisible = false
                profile_name.isEndIconVisible = false
                monthly_budget.error = null
                profile_name.error = null
            }

        }

    }

}
package com.anureet.expensemanager.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anureet.expensemanager.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if application is opened for the first time
        val sharedPreferences : SharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        var openedFirstTime: String? = sharedPreferences.getString("FirstTimeInstall","")

        if(openedFirstTime.equals("Yes")){

            // Code to Test AddTransactionFragment

//            val fragment = AddTransactionFragment()
//            val fragmentManager = supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//
//            fragmentTransaction.add(R.id.main_container,fragment)
//            fragmentTransaction.commit()

        }else{
            val fragment = OnboardingFragment()
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.add(R.id.main_container,fragment)
            fragmentTransaction.commit()

            val editor:SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("FirstTimeInstall","Yes")
            editor.apply()
        }
    }
}
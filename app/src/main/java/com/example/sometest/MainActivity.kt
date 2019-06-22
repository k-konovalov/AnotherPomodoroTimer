package com.example.sometest

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.sometest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private val dataForTimer: DataForTimer = DataForTimer(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Его надо было убирать т.к. биндим иначе все падает нахер
        //setContentView(R.layout.activity_main)

        try{
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        }
        // Set the value of the myName variable that is declared and used in the layout file.
        //binding.dataForTimer = dataForTimer
        catch (e: android.view.InflateException) {
            Log.e("MainActivity", "onCreate", e);
            throw Exception("Hi There!");
        }
        //val navController = this.findNavController(R.id.myNavHostFragment)
        //NavigationUI.setupActionBarWithNavController(this, navController)

    }
}

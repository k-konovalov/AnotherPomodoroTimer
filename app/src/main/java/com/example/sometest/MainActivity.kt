package com.example.sometest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

var cycle: Int = 0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
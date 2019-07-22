package com.example.sometest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

var cycle: Int = 0
const val APP_PREFERENCES ="app_settings"
const val REST_TIME = "rest_time"
const val WORK_TIME = "work_time"
const val WORK_CYCLES = "work_cycles"
const val BIG_BREAK = "big_break"
lateinit var pref: SharedPreferences
lateinit var editor: SharedPreferences.Editor
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        editor = pref.edit()
    }
}
package com.example.sometest.util

import android.content.Context
import android.preference.PreferenceManager

class PrefUtil {
    companion object{
        const val REST_TIME = "rest_time"
        fun getRestTime(context: Context):Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(REST_TIME,5)
        }
        fun setRestTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(REST_TIME,time)
            editor.apply()
        }
        const val WORK_TIME = "work_time"
        fun getWorkTime(context: Context): Long{
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            return preference.getLong(WORK_TIME,25)
        }
        fun setWorkTime(time: Long,context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(WORK_TIME,time)
            editor.apply()
        }
        const val WORK_CYCLES = "work_cycles"
        fun getWorkCycles(context: Context): Int{
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            return preference.getInt(WORK_CYCLES,4)
        }
        fun setWorkCycles(cycles: Int, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putInt(WORK_CYCLES, cycles)
            editor.apply()
        }
        const val BIG_BREAK = "big_break"
        fun getBigBreak(context: Context):Long{
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            return preference.getLong(BIG_BREAK,15)
        }
        fun setBigBreak(time: Long,context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(BIG_BREAK, time)
            editor.apply()
        }
    }
}
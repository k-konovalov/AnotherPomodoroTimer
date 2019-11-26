package com.example.sometest.util

class Converter {
    companion object{
        fun convertLong(string: String): Long = string.toLong()
        fun convertInt(string: String): Int = string.toInt()
    }
}
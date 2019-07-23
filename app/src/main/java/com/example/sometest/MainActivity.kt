package com.example.sometest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

var cycle: Int = 0
//Специально для тебя комменчу. чтобы знал что к чему
//1)Тут у нас константы для ключей, т.к. в преференсах хранится все в структуре ключ -> значение
const val APP_PREFERENCES ="app_settings"
const val REST_TIME = "rest_time"
const val WORK_TIME = "work_time"
const val WORK_CYCLES = "work_cycles"
const val BIG_BREAK = "big_break"
//2)Тут объявляю префы и эдитор, чтобы пользоваться ими во всем проекте. Лэйт инитом, потому что другого варианта не нашел.
//Кажется инициализировать его можно только в рантайме, но я могу и крупно ошибаться по этому поводу
lateinit var pref: SharedPreferences
lateinit var editor: SharedPreferences.Editor
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Так мы это дело получаем. Контекст мод прайват - по дефолту, до него имя для файлика
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        editor = pref.edit()
    }
}
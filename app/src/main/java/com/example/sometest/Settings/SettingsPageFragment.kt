package com.example.sometest.Settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sometest.*
import com.example.sometest.databinding.FragmentSettingsPageBinding

class SettingsPageFragment : Fragment() {
    lateinit var workTime :String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingsPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings_page,container,false
        )
        binding.btnSave.setOnClickListener {
            // тут с помощью эдитора кладем в преференсы значения, указывая ключ и собственно значение
            editor.putLong(WORK_TIME,Converter.convertLong(binding.txtForWorkTimer.text.toString()))
            editor.putLong(BIG_BREAK,Converter.convertLong(binding.txtForBigRest.text.toString()))
            editor.putInt(WORK_CYCLES,Converter.convertInt(binding.txtForCyclesBeforeRest.text.toString()))
            editor.putLong(REST_TIME,Converter.convertLong(binding.txtForRelaxTimer.text.toString()))
            //Как закончили класть данные в преференсы - обязательно апплай
            editor.apply()
                //v: View -> v.findNavController().navigate(SettingsPageFragmentDirections.actionSettingsPageToStartPage())
        }
        return binding.root
    }
    //TODO: Refactor if needed
    //Это тот самый костыль. Нам разные данные нужны, которые мы из текствью берем, в которых текст.
    //Конвертить внутри моделей стринги в нужные нам типы - не нуль безопасно. Я решил эту проблему вот так.
 class Converter{
     companion object{
         fun convertLong(string: String): Long = string.toLong()
         fun convertInt(string: String): Int = string.toInt()
     }

 }

}

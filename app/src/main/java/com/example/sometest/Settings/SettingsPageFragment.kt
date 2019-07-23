package com.example.sometest.Settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.sometest.*
import com.example.sometest.databinding.FragmentSettingsPageBinding

class SettingsPageFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingsPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings_page,container,false
        )

        binding.txtForRelaxTimer.setText(pref.getLong(REST_TIME,5).toString())
        binding.txtForCyclesBeforeRest.setText(pref.getInt(WORK_CYCLES,4).toString())
        binding.txtForBigRest.setText(pref.getLong(BIG_BREAK,15).toString())
        binding.txtForWorkTimer.setText(pref.getLong(WORK_TIME,25).toString())



        binding.btnSave.setOnClickListener {
            // 3)тут с помощью эдитора кладем в преференсы значения, указывая ключ и собственно значение
            editor.putLong(WORK_TIME,Converter.convertLong(binding.txtForWorkTimer.text.toString()))
            editor.putLong(BIG_BREAK,Converter.convertLong(binding.txtForBigRest.text.toString()))
            editor.putInt(WORK_CYCLES,Converter.convertInt(binding.txtForCyclesBeforeRest.text.toString()))
            editor.putLong(REST_TIME,Converter.convertLong(binding.txtForRelaxTimer.text.toString()))
            // 4)Как закончили класть данные в преференсы - обязательно апплай
            editor.apply()
            it.findNavController().navigate(SettingsPageFragmentDirections.actionSettingsPageToStartPage())
        }

        //Changing the work time
        binding.seekBarForWorkTimer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.txtForWorkTimer.setText(progress.toString())
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        //Changing the Big Rest time
        binding.seekBarForBigRest.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.txtForBigRest.setText(progress.toString())
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        //Changing the work cycles value
        binding.seekBarForCyclesBeforeRest.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.txtForCyclesBeforeRest.setText(progress.toString())
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        //Changing the rest time
        binding.seekBarForRelaxTimer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.txtForRelaxTimer.setText(progress.toString())
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

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

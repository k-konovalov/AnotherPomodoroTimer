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
            editor.putLong(WORK_TIME,Converter.convertLong(binding.txtForWorkTimer.text.toString()))
            editor.putLong(BIG_BREAK,Converter.convertLong(binding.txtForBigRest.text.toString()))
            editor.putInt(WORK_CYCLES,Converter.convertInt(binding.txtForCyclesBeforeRest.text.toString()))
            editor.putLong(REST_TIME,Converter.convertLong(binding.txtForRelaxTimer.text.toString()))
            editor.apply()
                //v: View -> v.findNavController().navigate(SettingsPageFragmentDirections.actionSettingsPageToStartPage())
        }
        return binding.root
    }
 class Converter{
     companion object{
         fun convertLong(string: String): Long = string.toLong()
         fun convertInt(string: String): Int = string.toInt()
     }

 }

}

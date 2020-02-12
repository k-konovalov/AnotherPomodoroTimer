package com.example.sometest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.sometest.*
import com.example.sometest.util.Converter
import com.example.sometest.util.PrefUtil
import com.example.sometest.databinding.FragmentSettingsPageBinding

class SettingsPageFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingsPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings_page,container,false
        )
        val context = activity!!.applicationContext
        binding.txtForRelaxTimer.setText(PrefUtil.getRestTime(context).toString())
        binding.txtForCyclesBeforeRest.setText(PrefUtil.getWorkCycles(context).toString())
        binding.txtForBigRest.setText(PrefUtil.getBigBreak(context).toString())
        binding.txtForWorkTimer.setText(PrefUtil.getWorkTime(context).toString())



        binding.btnSave.setOnClickListener {
            PrefUtil.setWorkTime(Converter.convertLong(binding.txtForWorkTimer.text.toString()),context)
            PrefUtil.setBigBreak(Converter.convertLong(binding.txtForBigRest.text.toString()),context)
            PrefUtil.setWorkCycles(Converter.convertInt(binding.txtForCyclesBeforeRest.text.toString()),context)
            PrefUtil.setRestTime(Converter.convertLong(binding.txtForRelaxTimer.text.toString()),context)
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

}

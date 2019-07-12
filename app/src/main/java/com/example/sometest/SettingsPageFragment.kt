package com.example.sometest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.sometest.databinding.FragmentSettingsPageBinding
import android.widget.SeekBar

class SettingsPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingsPageBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_settings_page,container,false
        )

        binding.btnSave.setOnClickListener {
                v: View -> v.findNavController().navigate(SettingsPageFragmentDirections.actionSettingsPageToStartPage())
        }
        return binding.root
    }


}

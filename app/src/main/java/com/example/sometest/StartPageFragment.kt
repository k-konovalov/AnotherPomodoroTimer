package com.example.sometest

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.sometest.databinding.FragmentStartPageBinding

class StartPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentStartPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start_page, container, false)
        binding.btnStart.setOnClickListener {
              v: View -> v.findNavController().navigate(StartPageFragmentDirections.actionStartPageToTimerRedState())
        }
        return binding.root
    }
}
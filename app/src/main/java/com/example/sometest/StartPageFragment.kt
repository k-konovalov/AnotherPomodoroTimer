package com.example.sometest

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.sometest.databinding.FragmentStartPageBinding
import com.example.sometest.timer.TimerViewModel.Companion.cycle

class StartPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentStartPageBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start_page, container, false)
        binding.btnStart.setOnClickListener {
              v: View ->
            cycle = 0
            v.findNavController().navigate(StartPageFragmentDirections.actionStartPageToTimerRedState())
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title == "Settings"){
            findNavController().navigate(StartPageFragmentDirections.actionStartPageToSettingsPage())
        }
        return super.onOptionsItemSelected(item)
    }
}
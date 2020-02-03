package com.example.sometest.Fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.sometest.R
import com.example.sometest.StartPageFragmentDirections
import com.example.sometest.databinding.FragmentStartPageBinding

class StartPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentStartPageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_start_page, container, false)
        binding.btnStart.setOnClickListener {
              v: View ->
            v.findNavController().navigate(StartPageFragmentDirections.actionStartPageToTimerRedState())
        }
        setHasOptionsMenu(true)

        return binding.startPage
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
package com.example.sometest.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sometest.R
import com.example.sometest.databinding.FragmentStartPageBinding
import com.example.sometest.util.TaskRecyclerAdapter

class StartPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentStartPageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_start_page, container, false)
        binding.btnStart.setOnClickListener {
              v: View ->
            v.findNavController().navigate(StartPageFragmentDirections.actionStartPageToTimerRedState())
        }
        binding.btnSelectTask.setOnClickListener{
            val view = inflater.inflate(R.layout.select_task,container,false)
            val recycler = view.findViewById<RecyclerView>(R.id.recycler)
            recycler.layoutManager = LinearLayoutManager(context!!)
            recycler.adapter = TaskRecyclerAdapter()

            AlertDialog.Builder(context)
                .setView(view)
                .setTitle("Recycler")
                .show()
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
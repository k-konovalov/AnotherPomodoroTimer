package com.example.sometest


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

private lateinit var currentTimer: Timer
private val overTime = 5

class GreenStateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //secondsCount = 0
        return inflater.inflate(R.layout.fragment_timer_green_state, container, false)
    }


}

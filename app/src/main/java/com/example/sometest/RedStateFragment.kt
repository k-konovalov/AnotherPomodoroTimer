package com.example.sometest


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.fragment.findNavController
import com.example.sometest.databinding.FragmentStartPageBinding
import kotlinx.android.synthetic.main.fragment_timer_red_state.*

private lateinit var currentTimer: Timer
private val overTime = 25

class RedStateFragment : Fragment(), LifecycleObserver {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Setup Timer, passing in the lifecycle
        // If there is a savedInstanceState bundle, then "restarting" the activity
        // If there isn't a bundle, then it's a "fresh" start
        currentTimer = Timer(this.lifecycle)

        currentTimer.startTimer(inflater.inflate(R.layout.fragment_timer_red_state, container, false),txtViewText, overTime)

        return inflater.inflate(R.layout.fragment_timer_red_state, container, false)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds_key", currentTimer.secondsCount)
        Log.i("MainActivity","onSaveInstanceState Called")
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
    }
    override fun onResume() {

        //ToDo Пробросить VIEW
        currentTimer.startTimer(currentTimer,txtViewText, overTime)
        Log.i("MainActivity","onResume Called")
        //findNavController().navigate(RedStateFragmentDirections.actionTimerRedStateToTimerGreenState())
        Log.i("MainActivity","${view}")
        super.onResume()
    }

}

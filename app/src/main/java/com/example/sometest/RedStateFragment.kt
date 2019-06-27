package com.example.sometest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import kotlinx.android.synthetic.main.fragment_timer_red_state.*

class RedStateFragment : Fragment(),LifecycleObserver {
    private lateinit var currentTimer: Timer
    private val overTime = 25

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentTimer = Timer(this.lifecycle)

        return inflater.inflate(R.layout.fragment_timer_red_state, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds_key", currentTimer.secondsCount)
        Log.i("MainActivity","onSaveInstanceState Called")
        super.onSaveInstanceState(outState)
    }

   override fun onResume() {
        currentTimer.startTimer(txtTextView, overTime)
        Log.i("MainActivity","onResume Called")
        super.onResume()
    }

    fun switchNav( view: View){
        //Navigation.findNavController(view).navigate(RedStateFragmentDirections.actionTimerRedStateToTimerGreenState())
    }

}

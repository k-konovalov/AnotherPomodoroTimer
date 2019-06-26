package com.example.sometest

import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController

//import timber.log.Timber

private const val TAG = "Timer"

class Timer(lifecycle: Lifecycle) : LifecycleObserver {

    // The number of seconds counted since the timer started
    var secondsCount = 0

    /**
     * [Handler] is a class meant to process a queue of messages (known as [android.os.Message]s)
     * or actions (known as [Runnable]s)
     */
    private var handler = Handler()
    private lateinit var runnable: Runnable


    init {
        // Add this as a lifecycle Observer, which allows for the class to react to changes in this
        // activity's lifecycle state
        lifecycle.addObserver(this)
    }

    //@OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startTimer( view: View, txtViewInFun: TextView,over: Int) {
        // Create the runnable action, which prints out a log and increments the seconds counter
        secondsCount = over
        runnable = Runnable {
            if (secondsCount==0){
                stopTimer()
                switchNav(view)
            }
            secondsCount--
            txtViewInFun.text = "$secondsCount"
            Log.i(TAG,"Timer is at : $secondsCount")
            // postDelayed re-adds the action to the queue of actions the Handler is cycling
            // through. The delayMillis param tells the handler to run the runnable in
            // 1 second (1000ms)
            handler.postDelayed(runnable, 1000)
        }

        // This is what initially starts the timer
        handler.postDelayed(runnable, 1000)

        // Note that the Thread the handler runs on is determined by a class called Looper.
        // In this case, no looper is defined, and it defaults to the main or UI thread.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stopTimer() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable)
    }
    fun switchNav( view: View){
        findNavController(view).navigate(RedStateFragmentDirections.actionTimerRedStateToTimerGreenState())
    }
}
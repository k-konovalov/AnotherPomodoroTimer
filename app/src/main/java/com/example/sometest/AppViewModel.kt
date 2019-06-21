package com.example.sometest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.CountDownTimer

class AppViewModel: ViewModel() {
    companion object{
        //Total time of the session
        private const val COUNTDOWN_TIME = 25000L

        //Milliseconds in one second
        private const val ONE_SECOND = 1000L

        // This is when the session is over
        private const val DONE = 0L
    }
    //Our timer
    private val timer: CountDownTimer

    //LiveData and encapsulation
    //Time
    private val _currentTime=MutableLiveData<Long>()
    val currentTime:LiveData<Long>
            get()=_currentTime

    //Event which triggers end of the countdown
    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean>
        get() = _eventCountDownFinish
    init {
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value=(millisUntilFinished/ ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventCountDownFinish.value=true
            }
        }
        timer.start()
    }
}
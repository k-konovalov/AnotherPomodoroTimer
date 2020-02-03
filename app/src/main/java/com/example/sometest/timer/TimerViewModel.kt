package com.example.sometest.timer

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.app.NotificationManagerCompat
import com.example.sometest.*
import com.example.sometest.TimerService.Companion.cycle
import com.example.sometest.util.*
import com.google.android.material.snackbar.Snackbar

class TimerViewModel : ViewModel() {
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    val POMODORO_DEFAULT_WORK_TIME = 25
    val POMODORO_DEFAULT_REST_TIME = 5
    private val MINUTE = 60000L
    val ONE_SECOND = 1000L //ms
    private val workTime = 7 * ONE_SECOND
    private val restTime = 4 * ONE_SECOND
    var currentMaxTime = 0L

    companion object {
        /*private const val workTime = MINUTE * POMODORO_DEFAULT_WORK_TIME
        private const val restTime = MINUTE * POMODORO_DEFAULT_REST_TIME*/
    }

    init {
        TimerService.timer = initTimer(workTime)
    }

    //Our timer
    private fun initTimer(time: Long) = object : CountDownTimer(time, ONE_SECOND) {
        override fun onTick(millisUntilFinished: Long) {
            _currentTime.value = (millisUntilFinished / ONE_SECOND)
        }

        override fun onFinish() {
            _eventCountDownFinish.value = true
        }

        init {
            Log.e("test time:", time.toString())
            currentMaxTime = time
            this.start()
        }
    }

    //LiveData and encapsulation
    //Time
    private val _currentTimerStatus = MutableLiveData<String>()
    val currentTimerStatus: LiveData<String>
        get() = _currentTimerStatus

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    //Event which triggers end of the countdown
    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean>
        get() = _eventCountDownFinish

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    init {
        _currentTimerStatus.value = "worktime"
    }

    fun onCountDownFinish() {
        _currentTime.value = 0L
        _eventBuzz.value = BuzzType.GAME_OVER
        _eventCountDownFinish.value = false
        timerRestart()
    }

    private fun timerRestart() {
        Log.e("test", "currentTimerStatus: " + _currentTimerStatus.value)
        when (_currentTimerStatus.value) {
            "resttime" -> {
                _currentTimerStatus.value = "worktime"
                initTimer(workTime)
                cycle++
            }
            "worktime" -> {
                _currentTimerStatus.value = "resttime"
                initTimer(restTime)
            }
            else -> return
        }
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    fun createSnack(view: View, cycle: Int) {
        val snack = Snackbar.make(
            view, "This is the $cycle cycle",
            Snackbar.LENGTH_LONG
        )//.setAction("Action", null)
        //snack.setActionTextColor(Color.WHITE)
        val snackView = snack.view
        snackView.setBackgroundColor(Color.BLACK)
        val textView = snackView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        textView.textSize = 28f
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snack.show()
    }
}
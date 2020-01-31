package com.example.sometest.timer

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.example.sometest.*
import com.example.sometest.util.*
import com.google.android.material.snackbar.Snackbar


class TimerViewModel : ViewModel() {
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    companion object {
        var cycle: Int = 0
        //Total time of the session
        const val POMODORO_DEFAULT_WORK_TIME = 25
        const val POMODORO_DEFAULT_REST_TIME = 5
        private const val MINUTE = 60000L
        private const val ONE_SECOND = 1000L //ms


        /*private const val workTime = MINUTE * POMODORO_DEFAULT_WORK_TIME
        private const val restTime = MINUTE * POMODORO_DEFAULT_REST_TIME*/

        private const val workTime = 10 * ONE_SECOND
        private const val restTime = 5 * ONE_SECOND

        private const val DONE = 0L
    }

    //Our timer
    private lateinit var timer:CountDownTimer
    private var currentTimerStatus = "worktime"

    //тут достаем из настроек нужное нам значение. По дефолту оно будет каноническим для Pomodoro техники
    private fun initTimer(time:Long) = object : CountDownTimer(time, ONE_SECOND){
        override fun onTick(millisUntilFinished: Long) {
            _currentTime.value = (millisUntilFinished / ONE_SECOND)
            MainActivity.updateCurrentNotification(
                workTime.toInt(),
                currentTime.value!!.toInt(),
                currentTimerStatus
            )
        }

        override fun onFinish() {
            _currentTime.value = DONE
            _eventBuzz.value = BuzzType.GAME_OVER
            _eventCountDownFinish.value = true
            MainActivity.removeNotificationProgressBar()
        }
    }

    //LiveData and encapsulation
    //Time
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

    fun onCountDownFinish() {
        _eventCountDownFinish.value = false
        timerRestart()
    }

    init {
        timer = initTimer(workTime)
        timer.start()
    }

    private fun timerRestart(){
        timer.cancel()
        if(currentTimerStatus == "worktime") {
            currentTimerStatus = "resttime"
            initTimer(restTime)}
        else {
            currentTimerStatus = "worktime"
            initTimer(workTime)}
        timer.start()
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
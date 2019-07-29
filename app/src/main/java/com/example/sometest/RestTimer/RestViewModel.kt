package com.example.sometest.RestTimer

import android.content.SharedPreferences
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sometest.*

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 1000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class RestViewModel:ViewModel(){
    companion object{
        //Total time of the session
        private const val COUNTDOWN_TIME = 60000L
        //Milliseconds in one second
        private const val ONE_SECOND = 1000L

        // This is when the session is over
        private const val DONE = 0L

        private var currentMaxWorkTime = when(cycle % pref.getInt(WORK_CYCLES,4)){
            0-> COUNTDOWN_TIME * pref.getLong(BIG_BREAK,15)
            else-> COUNTDOWN_TIME * pref.getLong(REST_TIME,5)
        }

    }
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    private val timer: CountDownTimer

    private val _currentTime= MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get()=_currentTime

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    //Event which triggers end of the countdown
    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean>
        get() = _eventCountDownFinish

    fun onCountDownFinish(){
        _eventCountDownFinish.value=false
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    init {

        timer = object : CountDownTimer(
            currentMaxWorkTime,
            ONE_SECOND
        ) {
            override fun onFinish() {
                _currentTime.value= DONE
                _eventCountDownFinish.value=true
                MainActivity.removeNotificationProgressBar()
            }

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value=(millisUntilFinished/ ONE_SECOND)
                MainActivity.updateCurrentNotification(currentMaxWorkTime.toInt(),currentTime.value!!.toInt(),"Rest Time")
            }
        }
        timer.start()
    }
}
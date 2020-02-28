package com.example.sometest.timer

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.CountDownTimer
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.app.NotificationManagerCompat
import com.example.sometest.*
import com.example.sometest.TimerService.Companion.cycle
import com.example.sometest.network.*
import com.example.sometest.util.*
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TimerViewModel : ViewModel() {
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    val TAG = "TimerViewModel"
    private val networkHelper = NetworkHelper()
    private var getWorklogsRequest: Call<DefaultWorklogsResponse>? = null
    private var addWorklogRequest: Call<DefaultWorklogsResponse>? = null

    val POMODORO_DEFAULT_WORK_TIME = 25
    val POMODORO_DEFAULT_REST_TIME = 5
    private val MINUTE = 60000L
    val ONE_SECOND = 1000L //ms
    private val workTime = 10 * ONE_SECOND
    private val restTime = 10 * ONE_SECOND
    var currentMaxTime = 0L

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
                ifWorklogsExist()
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

    private fun ifWorklogsExist(){
        networkHelper.cancelCurrentRequestIfNeeded(getWorklogsRequest)

        MainActivity.interceptorType = NetworkHelper.INTERCEPTOR_TYPE.GET_WORKLOG

        getWorklogsRequest = RestApi.getInstance()?.dataEndpoint()?.getWorklogs()
        getWorklogsRequest?.enqueue(object: Callback<DefaultWorklogsResponse> {
            override fun onResponse(
                @NonNull call: Call<DefaultWorklogsResponse>,
                @NonNull response: Response<DefaultWorklogsResponse>
            ) {
                checkIfWorklogsExist(response)
            }

            override fun onFailure(
                @NonNull call: Call<DefaultWorklogsResponse>,
                @NonNull t: Throwable
            ) {
                MainActivity.interceptorType = NetworkHelper.INTERCEPTOR_TYPE.EMPTY
                Log.e(TAG,"Baby don't hurt me",t)
            }
        })
    }

    private fun checkIfWorklogsExist(response: Response<DefaultWorklogsResponse>) {
        if (networkHelper.isSuccessfulAndBodyNotNull(response)) {
            Log.e(TAG, "Worklogs total ${response.body()?.total}")
            addWorklog()
        }
    }

    private fun addWorklog(){
        networkHelper.cancelCurrentRequestIfNeeded(addWorklogRequest)
        val time = (POMODORO_DEFAULT_WORK_TIME*ONE_SECOND).toInt() + (POMODORO_DEFAULT_REST_TIME*ONE_SECOND).toInt()
        val body = WorklogRequestDTO(time, "I did some work here", "${getRemainingTime()}")

        //Журнал работ не должен быть пуст.
        addWorklogRequest = RestApi.getInstance()?.dataEndpoint()?.addWorklog(body)
        addWorklogRequest?.enqueue(object: Callback<DefaultWorklogsResponse> {
            override fun onResponse(
                @NonNull call: Call<DefaultWorklogsResponse>,
                @NonNull response: Response<DefaultWorklogsResponse>
            ) {
                Log.e(TAG,"Add Worklog response: " + response.toString())
                Log.e(TAG,"Add Worklog response: " + response.body().toString())
                MainActivity.interceptorType = NetworkHelper.INTERCEPTOR_TYPE.EMPTY
            }

            override fun onFailure(
                @NonNull call: Call<DefaultWorklogsResponse>,
                @NonNull t: Throwable
            ) {
                MainActivity.interceptorType = NetworkHelper.INTERCEPTOR_TYPE.EMPTY
                Log.e(TAG,"Baby don't hurt me",t)
            }
        })
    }

    private fun getRemainingTime(): String? {
        // "2020-02-17T05:29:50.833+0000"
        val delegate = "yyyy-MM-ddThh:mm:ss.000+0000"
        return DateFormat.format(delegate, Calendar.getInstance().time).toString()
    }
}
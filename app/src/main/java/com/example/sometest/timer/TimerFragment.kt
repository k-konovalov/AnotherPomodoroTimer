package com.example.sometest.timer

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.sometest.R
import com.example.sometest.databinding.FragmentTimerBinding
import com.example.sometest.timer.TimerViewModel.Companion.cycle
import com.example.sometest.util.KEY_CYCLE


class TimerFragment : Fragment(), LifecycleObserver {
    private lateinit var viewModel: TimerViewModel
    private lateinit var binding: FragmentTimerBinding

    private val redColorBg by lazy { ContextCompat.getColor(context!!, R.color.backForRed) }
    private val greenColorBg by lazy { ContextCompat.getColor(context!!, R.color.backForGreen) }
    private val valueAnimator by lazy { ValueAnimator.ofArgb(greenColorBg, redColorBg) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_timer, container, false
        )
        if (savedInstanceState != null) cycle = savedInstanceState.getInt(KEY_CYCLE, 0)
        cycle++

        binding.apply {
            initSetOnClickListeners(this)
            initObserves(this)
            initValueAnimator(this)
        }

        //Bluetooth
        //mConnectThread.write(1)
        //jConnectThread.run()
        //jConnectThread.write(1)

        return binding.root
    }

    private fun initSetOnClickListeners(binding: FragmentTimerBinding){
        binding.txtTextView.setOnClickListener {
            viewModel.createSnack(it, cycle)
        }
        binding.btnStop.setOnClickListener {
            //TODO: Timer STOP
            cycle--
            viewModel.onCountDownFinish()
            viewModel.stopTimer()
            findNavController().navigate(R.id.action_timer_red_state_to_start_page)
        }
    }

    private fun initObserves(
        binding:FragmentTimerBinding
    ){
        viewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)

        viewModel.currentTime.observe(this, Observer { newTime ->
            binding.txtTextView.text = DateUtils.formatElapsedTime(newTime)
        })
        viewModel.eventCountDownFinish.observe(this, Observer { isFinished ->
            if (isFinished) {
                viewModel.onCountDownFinish()
            }
        })
        viewModel.eventBuzz.observe(this, Observer { buzzType ->
            if (buzzType != TimerViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })
        viewModel.currentTimerStatus.observe(this, Observer { status ->
            if (status == "worktime") valueAnimator.start()
            if (status == "resttime") valueAnimator.reverse()
        })
    }

    private fun initValueAnimator(binding: FragmentTimerBinding){
        valueAnimator.duration = 500
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { valueAnimator ->
            binding.timerLayout.setBackgroundColor(
                valueAnimator.animatedValue as Int
            )
        }
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        buzzer.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                this.vibrate(pattern, -1)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CYCLE, cycle)
    }
}

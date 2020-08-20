package com.example.sometest.fragments

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.sometest.MainActivity
import com.example.sometest.R
import com.example.sometest.TimerService
import com.example.sometest.TimerService.Companion.cycle
import com.example.sometest.databinding.FragmentTimerBinding
import com.example.sometest.timer.TimerViewModel
import com.example.sometest.util.CHANNEL_ID
import com.example.sometest.util.KEY_CYCLE
import com.example.sometest.util.notificationId

class TimerFragment : Fragment(), LifecycleObserver {
    private val viewModel by lazy { ViewModelProviders.of(this).get(TimerViewModel::class.java) }
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
        binding.txtTimerIssueSummary.text = MainActivity.currentIssueSummary
        //Bluetooth
        //mConnectThread.write(1)
        //jConnectThread.run()
        //jConnectThread.write(1)

        TimerService.builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_settings_black_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(context, TimerService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(intent)
        } else context?.startService(intent)

        TimerService.cycle = 0

        return binding.timerLayout
    }

    private fun initSetOnClickListeners(binding: FragmentTimerBinding) {
        binding.txtTextView.setOnClickListener {
            viewModel.createSnack(it, cycle)
        }
        binding.btnStop.setOnClickListener {
            //TODO: Timer STOP
            cycle--
            TimerService.timer.cancel()

            val intent = Intent(context, TimerService::class.java)
            activity?.stopService(intent)

            findNavController().navigate(R.id.action_timer_red_state_to_start_page)
        }
    }

    private fun initObserves(
        binding: FragmentTimerBinding
    ) {
        viewModel.currentTime.observe(viewLifecycleOwner, Observer { newTime ->
            binding.txtTextView.text = DateUtils.formatElapsedTime(newTime)
            updateCurrentNotification(
                viewModel.currentMaxTime.toInt() / viewModel.ONE_SECOND.toInt(),
                newTime.toInt(),
                viewModel.currentTimerStatus.value.toString()
            )
        })
        viewModel.currentTimerStatus.observe(viewLifecycleOwner, Observer { status ->
            if (status == "worktime") valueAnimator.start()
            if (status == "resttime") valueAnimator.reverse()
        })

        viewModel.eventCountDownFinish.observe(viewLifecycleOwner, Observer { isFinished ->
            if (isFinished) {
                viewModel.onCountDownFinish()
            }
        })
        viewModel.eventBuzz.observe(viewLifecycleOwner, Observer { buzzType ->
            if (buzzType != TimerViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })
    }

    private fun initValueAnimator(binding: FragmentTimerBinding) {
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

    private fun updateCurrentNotification(max: Int, progress: Int, currentStatus: String) {
        NotificationManagerCompat.from(requireContext()).apply {
            TimerService.builder.setProgress(max, progress, false)
                .setOnlyAlertOnce(true)
                .setContentTitle(currentStatus)
            //.setStyle(NotificationCompat.BigTextStyle)
            notify(notificationId, TimerService.builder.build())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CYCLE, cycle)
    }
}

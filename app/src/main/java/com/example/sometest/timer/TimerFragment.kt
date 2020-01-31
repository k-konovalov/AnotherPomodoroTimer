package com.example.sometest.timer

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.sometest.databinding.FragmentTimerRedStateBinding
import com.example.sometest.R
import com.example.sometest.timer.TimerViewModel.Companion.cycle
import com.example.sometest.util.KEY_CYCLE

class TimerFragment : Fragment(), LifecycleObserver {
    private lateinit var viewModel: TimerViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTimerRedStateBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_timer_red_state, container, false
        )

        if (savedInstanceState != null) {
            cycle = savedInstanceState.getInt(KEY_CYCLE, 0)
        }
        cycle++

        binding.apply {
            initSetOnClickListeners(this)
            initObserves(this)
        }

        //Bluetooth
        //mConnectThread.write(1)
        //jConnectThread.run()
        //jConnectThread.write(1)

        return binding.root
    }

    private fun initSetOnClickListeners(binding: FragmentTimerRedStateBinding){
        binding.txtTextView.setOnClickListener {
            viewModel.createSnack(it, cycle)
        }
        binding.btnStop.setOnClickListener {
            //TODO: Timer STOP
            cycle--
            viewModel.onCountDownFinish()
            findNavController().popBackStack(R.id.action_timer_red_state_to_start_page,false)
        }
    }

    private fun initObserves(
        binding:FragmentTimerRedStateBinding
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

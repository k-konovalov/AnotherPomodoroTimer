package com.example.sometest

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
import com.example.sometest.databinding.FragmentTimerRedStateBinding
import androidx.navigation.fragment.NavHostFragment.findNavController

class RedStateFragment : Fragment(),LifecycleObserver {

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentTimerRedStateBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_timer_red_state, container, false)

        viewModel=ViewModelProviders.of(this).get(AppViewModel::class.java)

        viewModel.currentTime.observe(this, Observer { newTime ->
            binding.txtTextView.text= DateUtils.formatElapsedTime(newTime)
        })

        viewModel.eventCountDownFinish.observe(this, Observer {  isFinished ->
            if(isFinished){
                val action = RedStateFragmentDirections.actionTimerRedStateToTimerGreenState()
                findNavController(this).navigate(action)
                viewModel.onCountDownFinish()
            }
        })
        // Buzzes when triggered with different buzz events
        viewModel.eventBuzz.observe(this, Observer { buzzType ->
            if (buzzType != AppViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })
        return binding.root
    }
    private fun oldBuzz(pattern: LongArray){

    }
    private fun buzz(pattern: LongArray) {
        activity?.getSystemService(Context.VIBRATOR_SERVICE)
        val buzzer = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        //val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
             //Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

}

package com.example.sometest.RestTimer


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
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.sometest.*
//import com.example.sometest.GreenStateFragmentDirections
import com.example.sometest.databinding.FragmentTimerGreenStateBinding


class GreenStateFragment : Fragment(), LifecycleObserver {

    private lateinit var viewModel: RestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTimerGreenStateBinding=DataBindingUtil.inflate(
            inflater, R.layout.fragment_timer_green_state,container,false
        )
        viewModel=ViewModelProviders.of(this).get(RestViewModel::class.java)

        viewModel.currentTime.observe(this, Observer {newTime ->
            binding.txtTextView.text= DateUtils.formatElapsedTime(newTime)
        })

        viewModel.eventCountDownFinish.observe(this, Observer{isFinished ->
            if(isFinished) {
                val action =
                    GreenStateFragmentDirections.actionTimerGreenStateToTimerRedState()
                findNavController(this).navigate(action)
                viewModel.onCountDownFinish()
            }
        })

        viewModel.eventBuzz.observe(this, Observer { buzzType ->
            if (buzzType != RestViewModel.BuzzType.NO_BUZZ) {
                buzz(buzzType.pattern)
                viewModel.onBuzzComplete()
            }
        })
        //mConnectThread.write(0)
        jConnectThread.run()
        jConnectThread.write(0)
        return binding.root
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        buzzer.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                buzzer.vibrate(pattern, -1)
            }
        }
    }


}

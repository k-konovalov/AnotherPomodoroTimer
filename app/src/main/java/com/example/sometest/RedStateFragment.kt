package com.example.sometest

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_timer_red_state.*

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

        return binding.root
    }


    fun switchNav( view: View){
        //Navigation.findNavController(view).navigate(RedStateFragmentDirections.actionTimerRedStateToTimerGreenState())
    }

}

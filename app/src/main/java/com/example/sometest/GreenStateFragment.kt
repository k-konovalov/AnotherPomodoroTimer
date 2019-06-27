package com.example.sometest


import android.os.Bundle
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.sometest.databinding.FragmentTimerGreenStateBinding


class GreenStateFragment : Fragment() {

    private lateinit var viewModel: RestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentTimerGreenStateBinding=DataBindingUtil.inflate(
            inflater,R.layout.fragment_timer_green_state,container,false
        )

        viewModel=ViewModelProviders.of(this).get(RestViewModel::class.java)

        viewModel.currentTime.observe(this, Observer {newTime ->
            binding.txtTextView.text= DateUtils.formatElapsedTime(newTime)
        })

        viewModel.eventCountDownFinish.observe(this, Observer{isFinished ->
            if(isFinished) {
                val action = GreenStateFragmentDirections.actionTimerGreenStateToTimerRedState()
                findNavController(this).navigate(action)
                viewModel.onCountDownFinish()
            }
        })
        return binding.root
    }


}

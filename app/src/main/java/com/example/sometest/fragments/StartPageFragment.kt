package com.example.sometest.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sometest.R
import com.example.sometest.databinding.FragmentStartPageBinding
import com.example.sometest.network.DefaultIssuesResponse
import com.example.sometest.network.DefaultWorklogsResponse
import com.example.sometest.network.IssueDTO
import com.example.sometest.network.RestApi
import com.example.sometest.util.TaskRecyclerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartPageFragment : Fragment() {
    val TAG = "StartPageFragment"
    private var getIssueRequest: Call<DefaultIssuesResponse<List<IssueDTO>>>? = null
    private var addWorklogRequest: Call<DefaultWorklogsResponse<List<IssueDTO>>>? = null
    private val taskRecyclerAdapter = TaskRecyclerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding:FragmentStartPageBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_start_page, container, false)
        binding.btnStart.setOnClickListener {
              v: View ->
            v.findNavController().navigate(StartPageFragmentDirections.actionStartPageToTimerRedState())
        }
        binding.btnSelectTask.setOnClickListener{
            val view = inflater.inflate(R.layout.select_task,container,false)
            val recycler = view.findViewById<RecyclerView>(R.id.recycler)
            recycler.layoutManager = LinearLayoutManager(context!!)
            recycler.adapter = taskRecyclerAdapter

            loadIssues()
            AlertDialog.Builder(context)
                .setTitle("Tasks")
                .setView(view)
                .show()
        }
        setHasOptionsMenu(true)

        return binding.startPage
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title == "Settings"){
            findNavController().navigate(StartPageFragmentDirections.actionStartPageToSettingsPage())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadIssues(){
        cancelCurrentRequestIfNeeded()

        val jqlQuery = "assignee=konovalov.k.a AND status='To Do'"
        getIssueRequest = RestApi.getInstance()?.dataEndpoint()?.getIssues(jqlQuery)
        getIssueRequest?.enqueue(object: Callback<DefaultIssuesResponse<List<IssueDTO>>> {
            override fun onResponse(
                @NonNull call: Call<DefaultIssuesResponse<List<IssueDTO>>>,
                @NonNull response: Response<DefaultIssuesResponse<List<IssueDTO>>>
            ) {
                checkResponseAndShowState(response)
            }

            override fun onFailure(
                @NonNull call: Call<DefaultIssuesResponse<List<IssueDTO>>>,
                @NonNull t: Throwable
            ) {
                Log.e(TAG,"Baby don't hurt me",t)
            }
        })
    }

    private fun cancelCurrentRequestIfNeeded() {
        if (getIssueRequest == null) {
            return
        }

        //check if request already cancelled
        if (getIssueRequest!!.isCanceled) {
            getIssueRequest = null
            return
        }

        //check if request executed OR already in queue
        if (getIssueRequest!!.isExecuted) {
            getIssueRequest!!.cancel()
            getIssueRequest = null
        }
    }

    private fun checkResponseAndShowState(response:Response<DefaultIssuesResponse<List<IssueDTO>>>) {
        if (!response.isSuccessful) {
            //showState(State.ServerError)
            Log.d(TAG,"Server error")
            return
        }

        val body:DefaultIssuesResponse<List<IssueDTO>>? = response.body()

        if (body == null) {
            //showState(State.HasNoData)
            Log.d(TAG,"Body null")
            return
        }

        val results:List<IssueDTO>? = body.issues
        if (results == null) {
            //showState(State.HasNoData)
            Log.d(TAG,"Data null")
            return
        }

        if (results.isEmpty()) {
            //showState(State.HasNoData)
            Log.d(TAG,"Data Empty")
            return
        }
        Log.d(TAG,"${results} triggered")
        taskRecyclerAdapter.replaceTasks(results)

        //showState(State.HasData)
    }
}
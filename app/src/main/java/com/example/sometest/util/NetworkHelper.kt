package com.example.sometest.util

import android.util.Log
import com.example.sometest.network.DefaultWorklogsResponse
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response

class NetworkHelper {
    enum class INTERCEPTOR_TYPE {
        WORKLOG,
        ISSUE,
        EMPTY
    }

    val TAG = "NetworkHelper"
    fun cancelCurrentRequestIfNeeded(request: Call<*>?) {
        request.apply {
            if (this == null) return
            if (this.isCanceled) return //check if request already cancelled
            if (this.isExecuted) this.cancel() //check if request executed OR already in queue
        }
    }

    fun isSuccessfulAndBodyNotNull(response:Response<*>):Boolean {
        if (!response.isSuccessful) {
            Log.d(TAG,"Server error")
            return false
        }

        val body = response.body()

        if (body == null) {
            Log.d(TAG,"Worklogs Body null")
            return false
        }

        return true
    }

    fun isResultDataNotNullOrEmpty(results: List<*>?): Boolean{
        results.apply {
            if (this == null) {
                Log.d(TAG,"Worklogs Data null")
                return false
            }
            if (this.isEmpty()){
                Log.d(TAG,"Worklogs Data Empty")
                return false
            }
        }
        Log.d(TAG,"${results} triggered")
        return true
    }
}
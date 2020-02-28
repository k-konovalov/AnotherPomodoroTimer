package com.example.sometest.network

import android.util.Log
import androidx.annotation.NonNull
import com.example.sometest.MainActivity
import com.example.sometest.util.API_KEY
import com.example.sometest.util.NetworkHelper
import com.example.sometest.util.USER
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApi() {
    companion object {

        private val URL = "https://vrarlab.atlassian.net/"
        private val TIMEOUT_IN_SECONDS = 2
        private var sRestApi: RestApi? = null

        fun getInstance(): RestApi?{
            //ToDo: Bug with creation of instance
            //if (sRestApi == null) {
                sRestApi = RestApi()
            //}

            return sRestApi
        }
    }

    private val dataEndpoint: DataEndpoint

    @NonNull
    private fun buildRetrofitClient(@NonNull client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @NonNull
    private fun buildOkHttpClient(): OkHttpClient {
        //Перехватчик запросов
        val networkLogInterceptor = HttpLoggingInterceptor()
        networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val smth = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(USER, API_KEY)) // Добавление пользователя \ ключа к каждому запросу
            .addInterceptor(networkLogInterceptor)
            //Настройка таймаутов
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
        //TODO: if getWorklog???
        Log.e("RestApi",MainActivity.interceptorType.toString())
        if (MainActivity.interceptorType == NetworkHelper.INTERCEPTOR_TYPE.GET_WORKLOG) {
            smth.addInterceptor(GetWorklogInterceptor(MainActivity.currentIssueId))
        }

        return smth.build()
    }

    init {
        val httpClient = buildOkHttpClient()
        val retrofit = buildRetrofitClient(httpClient)

        //init endpoints here. It's can be more then one endpoint
        dataEndpoint = retrofit.create(DataEndpoint::class.java)
    }

    fun dataEndpoint(): DataEndpoint = dataEndpoint
}

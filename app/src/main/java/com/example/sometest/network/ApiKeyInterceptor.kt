package com.example.sometest.network

import android.util.Base64
import androidx.annotation.NonNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset

class ApiKeyInterceptor(user: String, apiKey: String) : Interceptor {
    val base64auth = Base64.encodeToString(
        "$user:$apiKey".toByteArray(
            Charset.defaultCharset()
        ), Base64.NO_WRAP
    )

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Accept","application/json")
            //.addHeader("Content-Type","application/json")
            .addHeader("Authorization", "Basic $base64auth")
            .build()

        return chain.proceed(request)
    }
}

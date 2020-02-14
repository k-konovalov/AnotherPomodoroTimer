package com.example.sometest.network

import android.util.Base64
import androidx.annotation.NonNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset

class WorklogInterceptor(val issueId:String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        val requestWithoutNewPath = chain.request()

        val url = requestWithoutNewPath.url
            .newBuilder()
            .addPathSegment(issueId)
            .addPathSegment("worklog")
            .build()

        val requestWithAttachedPath = requestWithoutNewPath.newBuilder()
            .url(url)
            .build()

        return chain.proceed(requestWithAttachedPath)
    }
}

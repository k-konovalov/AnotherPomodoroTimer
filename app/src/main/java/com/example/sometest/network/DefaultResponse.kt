package com.example.sometest.network

import com.google.gson.annotations.SerializedName

data class DefaultIssuesResponse <T>(
    @SerializedName("issues") var issues: T? = null
)

data class DefaultWorklogsResponse <T>(
    @SerializedName("worklogs") var worklogs: T? = null
)
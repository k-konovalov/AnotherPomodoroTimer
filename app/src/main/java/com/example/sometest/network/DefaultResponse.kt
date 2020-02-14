package com.example.sometest.network

import com.google.gson.annotations.SerializedName

data class DefaultIssuesResponse <T>(
    @SerializedName("issues") var issues: T? = null
)

data class DefaultWorklogsResponse(
    @SerializedName("total") val total: Int? = null,
    @SerializedName("worklogs") var worklogs: List<OneWorklogDTO>? = null
)
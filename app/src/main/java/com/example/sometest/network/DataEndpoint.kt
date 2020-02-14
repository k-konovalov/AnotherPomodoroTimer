package com.example.sometest.network

import androidx.annotation.NonNull
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DataEndpoint {
    ///rest/api/2/issue/{issueIdOrKey}/worklog
    @NonNull
    @POST("/rest/api/3/issue")
    fun addWorklog(): Call<DefaultWorklogsResponse>

    @NonNull
    @GET("/rest/api/3/issue/")
    fun getWorklogs(): Call<DefaultWorklogsResponse>
    //Параметризованный запрос
    //fun search(@Query("") @NonNull search: String): Call<DefaultResponse<List<ImagesDTO>>>

    @NonNull
    @GET("rest/api/3/search")
    fun getIssues(@Query("jql") jql: String): Call<DefaultIssuesResponse<List<IssueDTO>>>
}
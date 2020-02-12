package com.example.sometest.network

import androidx.annotation.NonNull
import com.example.sometest.MainActivity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface DataEndpoint {
    @NonNull
    ///rest/api/2/issue/{issueIdOrKey}/worklog
    @POST("/rest/api/3/issue")
    fun addWorklog(): Call<DefaultWorklogsResponse<List<IssueDTO>>>
    //Параметризованный запрос
    //fun search(@Query("") @NonNull search: String): Call<DefaultResponse<List<ImagesDTO>>>

    @NonNull
    @GET("rest/api/3/search")
    fun getIssues(@Query("jql") jql: String): Call<DefaultIssuesResponse<List<IssueDTO>>>
}
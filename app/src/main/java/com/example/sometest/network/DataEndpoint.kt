package com.example.sometest.network

import androidx.annotation.NonNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DataEndpoint {
    ///rest/api/2/issue/{issueIdOrKey}/worklog
    @NonNull
    @Headers("Content-type: application/json;charset=UTF-8")
    @POST("/rest/api/2/issue/")
    fun addWorklog(@Body body: WorklogRequestDTO): Call<DefaultWorklogsResponse>

    @NonNull
    @GET("/rest/api/3/issue/")
    fun getWorklogs(): Call<DefaultWorklogsResponse>
    //Параметризованный запрос
    //fun search(@Query("") @NonNull search: String): Call<DefaultResponse<List<ImagesDTO>>>

    @NonNull
    @GET("rest/api/3/search")
    fun getIssues(@Query("jql") jql: String): Call<DefaultIssuesResponse<List<IssueDTO>>>
}
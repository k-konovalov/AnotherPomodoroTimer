package com.example.sometest.network

import com.google.gson.annotations.SerializedName

/** 1 get json для получения списка задач */

data class IssueDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("key") val key: String,
    @SerializedName("fields") val fields: FieldsDTO
)

data class FieldsDTO(
    @SerializedName("summary") val summary: String
)

/** 2 put json для добавления worklog */
//fields -> worklogs[] -> worklog{}
//@SerializedName("timespent") var timespent:Int? = null, //in seconds 25min = 1500 = 25 * 60s
//@SerializedName("worklog") var worklog:Int? = null, //in seconds 25min = 1500 = 25 * 60s

data class OneWorklogDTO(
    @SerializedName("worklogs") var url: String? = null
)


data class WorklogRequestDTO(
    @SerializedName("timeSpentSeconds") var timeSpentSeconds: Int? = null,
    @SerializedName("comment") var comment: String? = null,
    @SerializedName("started") var started: String? = null
)
//"timetracking": {
//"remainingEstimate": "0m",
//"timeSpent": "50m",
//"remainingEstimateSeconds": 0,
//"timeSpentSeconds": 3000
//},
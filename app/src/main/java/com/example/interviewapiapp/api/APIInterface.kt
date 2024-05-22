package com.example.interviewapiapp.api

import com.example.interviewapiapp.model.JSONUsers
import retrofit2.Response
import retrofit2.http.GET

interface APIInterface {

    @GET("users")
    suspend fun fetchDataFromAPI() : Response<List<JSONUsers>>
}
package com.stonetree.retrofit.example

import retrofit2.Call
import retrofit2.http.GET


interface LatencyService {
    @GET("/info/ping")
    fun ping(): Call<Unit>
}
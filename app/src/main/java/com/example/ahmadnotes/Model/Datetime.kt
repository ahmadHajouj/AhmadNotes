package com.example.ahmadnotes.Model

import retrofit2.Call
import retrofit2.http.GET

interface Datetime {
    @GET("api/timezone/Asia/Riyadh")
    fun getDatetime(): Call<Request>
}
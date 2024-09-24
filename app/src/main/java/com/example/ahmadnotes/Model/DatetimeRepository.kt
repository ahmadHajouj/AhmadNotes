package com.example.ahmadnotes.Model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DatetimeRepository {

    fun fetchDateTime(callback: (String?) -> Unit) {

        val api = Retrofit.Builder()
            .baseUrl("https://worldtimeapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Datetime::class.java)

        api.getDatetime().enqueue(object : Callback<Request> {
            override fun onResponse(call: Call<Request>, response: Response<Request>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val theDatetime = it.datetime.substring(0..15).replace('T', ' ')
                        callback(theDatetime)
                    } ?: callback(null)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Request>, t: Throwable) {
                callback(null)
            }
        })

    }
}

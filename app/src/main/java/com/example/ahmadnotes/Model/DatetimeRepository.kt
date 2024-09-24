package com.example.ahmadnotes.Model

import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DatetimeRepository {

     fun fetchDateTime() :String? {
            val url = URL("https://worldtimeapi.org/api/Asia/Riyadh")
            val connection = url.openConnection() as HttpURLConnection

            if (connection.responseCode == 200){
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8",)
                val req = Gson().fromJson(inputStreamReader, Request::class.java)
                inputStreamReader.close()
                inputSystem.close()
                return req.datetime.substring(0..15).replace('T', ' ')
            }else{
                return null
            }

    }


}
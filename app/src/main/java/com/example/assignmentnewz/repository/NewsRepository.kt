package com.example.assignmentnewz.repository

import android.app.Application
import com.example.assignmentnewz.dataClass.News
import com.example.assignmentnewz.retrofit.NewsInterface
import com.example.assignmentnewz.retrofit.RetroService
import retrofit2.Call

class NewsRepository constructor(val application: Application) {

    fun getServicesApiCall(query: String): Call<News> {
        return RetroService.retroInstance.getHeadlines("in",query)
    }
}
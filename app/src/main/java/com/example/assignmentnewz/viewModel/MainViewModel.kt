package com.example.assignmentnewz.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignmentnewz.dataClass.Article
import com.example.assignmentnewz.dataClass.News
import com.example.assignmentnewz.repository.NewsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(application: Application) : AndroidViewModel(application) {

    val repoInstance = NewsRepository(application)
    lateinit var news: Call<News>
    val news_ = MutableLiveData<News>()
    lateinit var searchNews: Call<News>
    val searchNews_ = MutableLiveData<News>()
    var articles: MutableList<Article> = mutableListOf()
    var searchArticles: MutableList<Article> = mutableListOf()

    fun getNews(query: String): MutableLiveData<News> {
        this.news = repoInstance.getServicesApiCall(query)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    articles.addAll(news.articles)
                    news_.value = News(articles)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("batao", "Error in fetching", t)
            }
        })
        return news_
    }

    fun getSearchNews(query: String): MutableLiveData<News> {
        this.searchNews = repoInstance.getSearchNews(query)
        searchNews.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    searchArticles.clear()
                    searchArticles.addAll(news.articles)
                    searchNews_.value = News(searchArticles)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("batao", "Error in fetching", t)
            }
        })
        return searchNews_
    }

}

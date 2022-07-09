package com.example.assignmentnewz.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentnewz.R
import com.example.assignmentnewz.dataClass.Article
import com.google.android.material.snackbar.Snackbar


class NewsRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    var articles_: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.newslist_item, parent, false)
        return NewsViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles_[position]
        if (!TextUtils.isEmpty(article.author)) {
            holder.source.text = article.author
        } else {
            holder.source.text = "Unknown Source"
        }

        if (!TextUtils.isEmpty(article.description)){
            holder.description.text = article.description
        }else{
            holder.description.text = "No Description.."
        }

        holder.clickableLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("url", article.url)
            it.findNavController().navigate(R.id.webViewFragment, bundle)
        }

        holder.title.text = article.title
        Glide.with(context).load(article.urlToImage)
            .fallback(R.drawable.unnamed).into(holder.newsImage)
            val publishTime: String = article.publishedAt
            val date = publishTime.slice(0..9)
            val time = publishTime.slice(11..18)
        holder.date.text = "$date At $time"
    }

    override fun getItemCount(): Int {
        return articles_.size
    }


    inner class NewsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsImage : ImageView = itemView.findViewById(R.id.newsImage)
        val description :TextView = itemView.findViewById(R.id.description)
        val title : TextView = itemView.findViewById(R.id.newsTitle)
        val clickableLayout : ConstraintLayout = itemView.findViewById(R.id.clickableLayout)
        val source : TextView= itemView.findViewById(R.id.source)
        val date : TextView= itemView.findViewById(R.id.dateTime)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNews(newss: ArrayList<Article>) {
        this.articles_ = newss
        notifyDataSetChanged()
    }

}
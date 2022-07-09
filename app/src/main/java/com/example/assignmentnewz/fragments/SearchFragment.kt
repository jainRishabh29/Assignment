package com.example.assignmentnewz.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentnewz.R
import com.example.assignmentnewz.adapter.NewsRecyclerAdapter
import com.example.assignmentnewz.dataClass.Article
import com.example.assignmentnewz.databinding.FragmentMainBinding
import com.example.assignmentnewz.databinding.FragmentSearchBinding
import com.example.assignmentnewz.viewModel.MainViewModel
import java.time.LocalDate

class SearchFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var adapter: NewsRecyclerAdapter
    lateinit var recyclerView: RecyclerView
    var articles: ArrayList<Article> = ArrayList()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.searchRV
        adapter = NewsRecyclerAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        binding.searchNews.addTextChangedListener(textWatcher)

        return view
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { str ->
                if (str.isNotEmpty()) {
                    Log.d("batao",str.toString())
                    viewModel.getSearchNews(str.toString()).observe(viewLifecycleOwner) {
                        if (it != null) {
                            Log.d("batao","get")
                            articles = it.articles as ArrayList<Article>
                            Log.d("batao",articles.toString())
                            adapter.setNews(articles)
                            Log.d("batao","change")
                        } else {

                        }
                    }
                }
            }
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
}
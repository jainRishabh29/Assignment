package com.example.assignmentnewz.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentnewz.R
import com.example.assignmentnewz.adapter.NewsRecyclerAdapter
import com.example.assignmentnewz.dataClass.Article
import com.example.assignmentnewz.databinding.FragmentMainBinding
import com.example.assignmentnewz.viewModel.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var adapter: NewsRecyclerAdapter
    lateinit var recyclerView: RecyclerView
    var articles: ArrayList<Article> = ArrayList()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val firebase = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = binding.recyclerView
        adapter = NewsRecyclerAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.getNews("").observe(viewLifecycleOwner, Observer {
            if (it != null) {
                articles = it.articles as ArrayList<Article>
                adapter.setNews(articles)
            }
        })
        binding.logout.setOnClickListener {
            firebase.signOut()
            findNavController().navigate(R.id.action_mainFragment_to_wrapperFragment)
        }
        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        (requireActivity() as AppCompatActivity).window.statusBarColor = ResourcesCompat.getColor(resources, R.color.statusBarC, null)
    }
}
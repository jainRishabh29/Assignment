package com.example.assignmentnewz.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.adapters.RadioGroupBindingAdapter
import com.example.assignmentnewz.R
import com.example.assignmentnewz.adapter.ViewPagerAdapter
import com.example.assignmentnewz.databinding.FragmentLogInBinding
import com.example.assignmentnewz.databinding.FragmentWrapperBinding
import com.google.android.material.tabs.TabLayoutMediator

class WrapperFragment : Fragment() {


    private var _binding: FragmentWrapperBinding    ? = null
    private val binding get() = _binding!!
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWrapperBinding.inflate(inflater, container, false)
        val view = binding.root
        val mRadioGroup = binding.rgLog
        val viewPager = binding.viewPagerLay

        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

        return view
    }
}
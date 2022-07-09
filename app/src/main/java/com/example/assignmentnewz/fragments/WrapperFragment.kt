package com.example.assignmentnewz.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.assignmentnewz.R
import com.example.assignmentnewz.adapter.ViewPagerAdapter
import com.example.assignmentnewz.databinding.FragmentWrapperBinding


class WrapperFragment : Fragment() {


    private var _binding: FragmentWrapperBinding? = null
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

        Toast.makeText(context,"swipe to change",Toast.LENGTH_LONG
        ).show()
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                Log.d("rb", "onPageSelected: $position")

                when (position) {
                    0 -> {
                        binding.rbLogin.isChecked = true
                        binding.rbSignup.isChecked = false
                    }
                    1 -> {
                        binding.rbLogin.isChecked = false
                        binding.rbSignup.isChecked = true
                    }
                }

                super.onPageSelected(position)
            }
        })
        return view
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).window.statusBarColor = ResourcesCompat.getColor(resources, R.color.wrapperStatusBarC, null)
    }
}
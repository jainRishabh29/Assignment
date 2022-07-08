package com.example.assignmentnewz.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.assignmentnewz.fragments.LogInFragment
import com.example.assignmentnewz.fragments.SignUpFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle : Lifecycle) : FragmentStateAdapter(fragmentManager ,lifecycle) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return  when (position){
            0-> {
                LogInFragment()
            }
            1->{
                SignUpFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}
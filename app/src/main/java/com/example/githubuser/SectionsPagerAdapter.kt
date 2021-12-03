package com.example.githubuser

import android.content.Context
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

//constructor yang diperlukan yaitu AppCompatActivity karena kita menggunakan Activity.
//Apabila Anda menerapkannya di Fragment, gunakanFragmentActivity.

class SectionsPagerAdapter(activity:AppCompatActivity):FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

}


package com.example.mygithubuser.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mygithubuser.ui.fragment.FollowersFragment
import com.example.mygithubuser.ui.fragment.FollowingFragment

class SectionPagerAdapter(private val fragmentActivity: FragmentActivity, private val data: Bundle) : FragmentStateAdapter(fragmentActivity) {

    private var fragmentBundle: Bundle = data

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment()
            1 -> FollowingFragment()
            else -> throw IllegalArgumentException("Invalid fragment position: $position")
        }.apply {
            arguments = fragmentBundle
        }
    }
}
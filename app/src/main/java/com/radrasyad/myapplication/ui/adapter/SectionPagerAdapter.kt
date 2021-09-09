package com.radrasyad.myapplication.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.radrasyad.myapplication.ui.DetailUserActivity
import com.radrasyad.myapplication.ui.FollowersFragment
import com.radrasyad.myapplication.ui.FollowingFragment


class SectionPagerAdapter(activity: DetailUserActivity, data: Bundle) : FragmentStateAdapter(activity){

    private var fragmentBundle: Bundle

    init {
        fragmentBundle = data
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }
}
package com.radrasyad.myapplication.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.radrasyad.myapplication.ui.detail.DetailUserActivity
import com.radrasyad.myapplication.ui.followers.FollowersFragment
import com.radrasyad.myapplication.ui.following.FollowingFragment


class SectionPagerAdapter(activity: DetailUserActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }
}
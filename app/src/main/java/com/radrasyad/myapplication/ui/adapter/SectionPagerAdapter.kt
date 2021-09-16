package com.radrasyad.myapplication.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.radrasyad.myapplication.ui.DetailUserActivity
import com.radrasyad.myapplication.ui.FollowersFragment
import com.radrasyad.myapplication.ui.FollowingFragment


class SectionPagerAdapter(activity: DetailUserActivity ) : FragmentStateAdapter(activity){


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }

        return fragment as Fragment
    }
}
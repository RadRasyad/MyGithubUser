package com.radrasyad.myapplication.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.radrasyad.myapplication.R
import com.radrasyad.myapplication.databinding.ActivityDetailUserBinding
import com.radrasyad.myapplication.ui.Setting
import com.radrasyad.myapplication.ui.followers.FollowersViewModel
import com.radrasyad.myapplication.ui.following.FollowingViewModel
import com.radrasyad.myapplication.ui.adapter.SectionPagerAdapter

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel


    companion object{
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_folowing,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        Log.d("UserData", "usernamenya: $username")


        supportActionBar?.title = "$username"

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        followersViewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        followersViewModel.setListFollowers(username!!)
        followingViewModel.setListFollowing(username)

        initPageAdapter()

        viewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]

        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this, {
            if (it !=null){
                binding.apply {
                    detailFname.text = it.name
                    detailUsername.text = it.login
                    repository.text = it.public_repos.toString()
                    txtFollower.text = it.followers.toString()
                    txtFollowing.text = it.following.toString()
                    tvcompany.text = it.company
                    tvlocation.text = it.location
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .placeholder(android.R.drawable.progress_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(detailImg)
                }
            }

        })
    }

    private fun initPageAdapter() {
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager2)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.detailtab)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.action_settings -> {
                val mSetting = Intent(this, Setting::class.java)
                startActivity(mSetting)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
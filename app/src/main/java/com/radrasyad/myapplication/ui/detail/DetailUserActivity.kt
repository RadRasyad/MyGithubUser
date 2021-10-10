package com.radrasyad.myapplication.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.radrasyad.myapplication.helper.ViewModelFactory
import com.radrasyad.myapplication.ui.setting.Setting
import com.radrasyad.myapplication.ui.followers.FollowersViewModel
import com.radrasyad.myapplication.ui.following.FollowingViewModel
import com.radrasyad.myapplication.ui.adapter.SectionPagerAdapter
import com.radrasyad.myapplication.ui.favorite.FavoriteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followingViewModel: FollowingViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"

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
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val img = intent.getStringExtra(EXTRA_AVATAR)

        followersViewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)

        if (username != null) {
            followersViewModel.setListFollowers(username)
        }
        if (username != null) {
            followingViewModel.setListFollowing(username)
        }

        supportActionBar?.title = "$username"
        initAppbar()
        initPageAdapter()

        viewModel = obtainViewModel(this)

        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    detailFname.text = it.name
                    detailUsername.text = it.login
                    repository.text = it.public_repos.toString()
                    txtFollower.text = it.followers.toString()
                    txtFollowing.text = it.following.toString()
                    tvcompany.text = it.company ?: "-"
                    tvlocation.text = it.location ?: "-"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .placeholder(android.R.drawable.progress_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(detailImg)

                    var isFavorite = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val count = viewModel.checkUser(id)
                        withContext(Dispatchers.Main) {
                            if (count > 0) {
                                setStatusFavorite(true)
                                isFavorite = true
                            } else {
                                setStatusFavorite(false)
                            }
                        }
                    }

                    fabFavorite.setOnClickListener {
                        isFavorite = !isFavorite

                        if (isFavorite) {
                            if (username != null) {
                                viewModel.addToFavorite(username, id, img)
                            }
                            Toast.makeText(
                                this@DetailUserActivity,
                                "Favorited",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            viewModel.removeFromFavorite(id)
                            Toast.makeText(
                                this@DetailUserActivity,
                                "Unfavorited",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        setStatusFavorite(isFavorite)
                    }

                }
            }

        })

    }

    private fun initAppbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
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

            R.id.action_favourite -> {
                val mFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(mFavorite)
                true
            }

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

    private fun setStatusFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_heart_2_fill)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_heart_add_line)
        }
    }

}
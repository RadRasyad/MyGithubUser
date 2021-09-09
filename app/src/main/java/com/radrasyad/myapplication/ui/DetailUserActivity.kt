package com.radrasyad.myapplication.ui

import android.graphics.drawable.DrawableContainer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.radrasyad.myapplication.R
import com.radrasyad.myapplication.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private  lateinit var viewModel: DetailUserViewModel

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


        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viepager2)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.detailtab)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val username = intent.getStringExtra(EXTRA_USERNAME)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        username?.let { viewModel.setUserDetail(it) }
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
            } else{
                binding.apply {
                    tvcompany.text = "-"
                    tvlocation.text = "-"
                }

            }
        })
    }
}
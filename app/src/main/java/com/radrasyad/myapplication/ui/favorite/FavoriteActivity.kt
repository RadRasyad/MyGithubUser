package com.radrasyad.myapplication.ui.favorite

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.radrasyad.myapplication.R
import com.radrasyad.myapplication.data.data.model.Favorite
import com.radrasyad.myapplication.data.data.model.Users
import com.radrasyad.myapplication.databinding.ActivityFavoriteBinding
import com.radrasyad.myapplication.helper.ViewModelFactory
import com.radrasyad.myapplication.ui.adapter.UsersAdapter
import com.radrasyad.myapplication.ui.detail.DetailUserActivity
import com.radrasyad.myapplication.ui.detail.DetailUserActivity.Companion.EXTRA_AVATAR
import com.radrasyad.myapplication.ui.detail.DetailUserActivity.Companion.EXTRA_ID
import com.radrasyad.myapplication.ui.detail.DetailUserActivity.Companion.EXTRA_USERNAME
import com.radrasyad.myapplication.ui.setting.Setting

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAppbar()
        binding.favRvUser.setHasFixedSize(true)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        favoriteViewModel = obtainViewModel(this@FavoriteActivity)

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(EXTRA_USERNAME, data.login)
                    it.putExtra(EXTRA_ID, data.id)
                    it.putExtra(EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            favRvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            favRvUser.setHasFixedSize(true)
            favRvUser.adapter = adapter
        }

        favoriteViewModel.getAllFavorites()?.observe(this, { favoriteList ->
            if (favoriteList != null) {
                if (favoriteList.isNotEmpty()) {
                    val userList = mapList(favoriteList)
                    adapter.setList(userList)
                    showEmpty(false)
                } else {
                    showEmpty(true)
                }
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun mapList(listFavorites: List<Favorite>): ArrayList<Users> {
        val listUser = ArrayList<Users>()
        for (user in listFavorites) {
            val userMapped = user.username?.let {
                user.avatarUrl?.let { it1 ->
                    Users(
                        login = it,
                        id = user.id,
                        avatar_url = it1,
                    )
                }
            }
            userMapped?.let { listUser.add(it) }
        }
        return listUser
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
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

    private fun initAppbar() {

        setSupportActionBar(findViewById(R.id.fav_toolbar))

        binding.toolbarLayout.title = "Favorite User"
        binding.toolbarLayout.setExpandedTitleColor(Color.WHITE)
        binding.toolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
        binding.favToolbar.overflowIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_more_vert_24)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showEmpty(state: Boolean) {
        if (state) {
            binding.favEState.root.visibility = View.VISIBLE
            binding.favRvUser.visibility = View.GONE
        } else {
            binding.favEState.root.visibility = View.INVISIBLE
            binding.favRvUser.visibility = View.VISIBLE
        }

    }

}
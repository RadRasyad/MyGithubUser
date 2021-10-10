package com.radrasyad.myapplication.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.radrasyad.myapplication.R
import com.radrasyad.myapplication.data.data.model.Users
import com.radrasyad.myapplication.databinding.ActivityMainBinding
import com.radrasyad.myapplication.ui.setting.Setting
import com.radrasyad.myapplication.ui.detail.DetailUserActivity
import com.radrasyad.myapplication.ui.adapter.UsersAdapter
import com.radrasyad.myapplication.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UsersAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAppbar()

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        searchUser()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            svUser.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                if (it.size > 0) {
                    adapter.setList(it)
                    showLoading(false)
                    showEmpty(false)
                    showNotFound(false)
                } else {
                    showNotFound(true)
                }
            }
        })
    }

    private fun initAppbar() {
        setSupportActionBar(findViewById(R.id.toolbar))

        binding.toolbarLayout.title = title
        binding.rvUser.setHasFixedSize(true)
        binding.toolbarLayout.setExpandedTitleColor(Color.WHITE)
        binding.toolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
        binding.toolbar.overflowIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_more_vert_24)
    }

    private fun searchUser() {
        binding.apply {
            svUser.queryHint = resources.getString(R.string.search_hint)
            svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (it.isNotEmpty()) {
                            viewModel.setSearchUsers(query)
                            showLoading(true)
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean = true
            })
            showEmpty(true)
            svUser.setOnCloseListener {
                svUser.setQuery("", false)
                true
            }
        }
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

            R.id.action_settings -> {
                val mSetting = Intent(this, Setting::class.java)
                startActivity(mSetting)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbar.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
            showNotFound(false)
            showEmpty(false)
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }

    private fun showEmpty(state: Boolean) {
        if (state) {
            binding.eState.root.visibility = View.VISIBLE
        } else {
            binding.eState.root.visibility = View.INVISIBLE
        }
    }

    private fun showNotFound(state: Boolean) {
        if (state) {
            binding.nfState.root.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        } else {
            binding.nfState.root.visibility = View.INVISIBLE
            binding.rvUser.visibility = View.VISIBLE
        }

    }

}
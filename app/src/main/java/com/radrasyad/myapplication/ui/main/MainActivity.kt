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
import com.radrasyad.myapplication.ui.Setting
import com.radrasyad.myapplication.ui.detail.DetailUserActivity
import com.radrasyad.myapplication.ui.adapter.UsersAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UsersAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        binding.toolbarLayout.title = title
        binding.rvUser.setHasFixedSize(true)
        binding.toolbarLayout.setExpandedTitleColor(Color.WHITE)
        binding.toolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
        binding.toolbar.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_more_vert_24)

        adapter = UsersAdapter(listUser = ArrayList())
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UsersAdapter.OnitemClickCallback{
            override fun onItemClicked(data: Users) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        searchUser()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            svUser.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUsers().observe(this, {
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
                showEmpty(false)
            } else {
                showLoading(false)
            }
        })
    }

    private fun searchUser() {
        binding.apply {
            svUser.queryHint = resources.getString(R.string.search_hint)
            svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let{
                        if (it.isNotEmpty()){
                            viewModel.setSearchUsers(query)
                            showLoading(true)
                        } else {
                            showEmpty(true)
                        }
                    }
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean = true
            })
            showEmpty(true)
            svUser.setOnCloseListener{
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
            R.id.action_settings -> {
                val mSetting = Intent(this, Setting::class.java)
                startActivity(mSetting)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.progressbar.visibility = View.VISIBLE
            showEmpty(false)
        } else {
            binding.progressbar.visibility = View.GONE
            showEmpty(true)
        }
    }

    private fun showEmpty(state: Boolean) {
        if (state) {
            binding.eState.root.visibility = View.VISIBLE
        } else {
            showEmpty(true)
            binding.eState.root.visibility = View.INVISIBLE
        }
    }

}
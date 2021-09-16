package com.radrasyad.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.radrasyad.myapplication.R
import com.radrasyad.myapplication.databinding.FragmentFollowingBinding
import com.radrasyad.myapplication.ui.adapter.UsersAdapter

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding : FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UsersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFollowingBinding.bind(view)

        adapter = UsersAdapter(listUser = ArrayList())
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(requireActivity())[FollowingViewModel::class.java]
        viewModel.getListFollowing().observe(requireActivity(), {
            if (it!=null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }

}
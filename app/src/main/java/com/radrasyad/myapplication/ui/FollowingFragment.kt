package com.radrasyad.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.radrasyad.myapplication.R
import com.radrasyad.myapplication.databinding.FragmentFollBinding

class FollowingFragment : Fragment(R.layout.fragment_foll) {

    private var _binding : FragmentFollBinding? = null
    private  val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollBinding.bind(view)
    }
}
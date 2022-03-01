package com.radrasyad.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.radrasyad.myapplication.data.model.Users
import com.radrasyad.myapplication.databinding.UserRowBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var listUser = ArrayList<Users>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onitemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onitemClickCallback
    }

    fun setList(user: ArrayList<Users>) {
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: UserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Users) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .placeholder(android.R.drawable.progress_horizontal)
                    .error(android.R.drawable.stat_notify_error)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(userImg)
                tvUsername.text = user.login
                binding.root.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}
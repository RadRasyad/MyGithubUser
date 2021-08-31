package com.radrasyad.myapplication.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.radrasyad.myapplication.data.model.Users
import com.radrasyad.myapplication.databinding.UserRowBinding

class UsersAdapter(private val listUser: ArrayList<Users>) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    fun setList(user: ArrayList<Users>){
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: UserRowBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(user: Users){
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(userImg)

                tvUsername.text = user.login
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

}
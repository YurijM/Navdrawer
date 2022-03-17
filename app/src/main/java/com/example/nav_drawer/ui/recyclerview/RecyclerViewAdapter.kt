package com.example.nav_drawer.ui.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nav_drawer.R
import com.example.nav_drawer.databinding.ItemUserRecyclerviewBinding
import com.example.nav_drawer.ui.recyclerview.model.User

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    var users: List<User> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class RecyclerViewHolder(
        val binding: ItemUserRecyclerviewBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserRecyclerviewBinding.inflate(inflater, parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val user = users[position]

        with(holder.binding) {
            rvUserName.text = user.name
            rvUserCompany.text = user.company

            if (user.photo.isNotBlank()) {
                Glide.with(rvUserPhoto.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(rvUserPhoto)
            } else {
                rvUserPhoto.setImageResource(R.drawable.ic_user)
            }
        }
    }
}
package com.example.nav_drawer.ui.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nav_drawer.R
import com.example.nav_drawer.databinding.ItemUserRecyclerviewBinding
import com.example.nav_drawer.ui.recyclerview.model.User

interface UserActionListener {
    fun onUserMove(user: User, moveBy: Int)
    fun onUserDelete(user: User)
    fun onUserDetails(user: User)
}
class RecyclerViewAdapter(
    private var actionListener: UserActionListener
) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(), View.OnClickListener {
    var users: List<User> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class RecyclerViewHolder(
        val binding: ItemUserRecyclerviewBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val user = v.tag as User

        when (v.id) {
            R.id.rvUserMore -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserRecyclerviewBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.rvUserMore.setOnClickListener(this)

        return RecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val user = users[position]

        holder.itemView.tag = user  //в атрибуте tag компонента сохраняем указатель на пользователя,
                                    //чтобы иметь возможность получить к нему доступ при обработке слушателя

        with(holder.binding) {
            rvUserMore.tag = user   //в атрибуте tag компонента сохраняем указатель на пользователя,
                                    //чтобы иметь возможность получить к нему доступ при обработке слушателя
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

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context //чтобы получить доступ к ресурсам
        val user = v.tag as User
        val position = users.indexOfFirst { it.id == user.id }

        popupMenu.menu.add(0, ID_MORE_UP, Menu.NONE, context.getString(R.string.popup_menu_move_up)).apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MORE_DOWN, Menu.NONE, context.getString(R.string.popup_menu_move_down)).apply {
            isEnabled = position < users.size - 1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.popup_menu_remove))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MORE_UP -> {
                    actionListener.onUserMove(user, -1)
                }
                ID_MORE_DOWN -> {
                    actionListener.onUserMove(user, 1)

                }
                ID_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
            }

            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val ID_MORE_UP = 1
        private const val ID_MORE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}
package com.example.nav_drawer.ui.list.baseadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.nav_drawer.databinding.ItemUserBinding

typealias OnDeleteListener = (User) -> Unit

class ListBaseAdapter(
    private val users: List<User>,
    private val onDeleteListener: OnDeleteListener
) : BaseAdapter(), View.OnClickListener {
    override fun getCount(): Int {
        return users.size
    }

    override fun getItem(p0: Int): Any {
        return users[p0]
    }

    override fun getItemId(p0: Int): Long {
        return users[p0].id
    }

    // Преобразование элементов данных в элементы списка
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val binding: ItemUserBinding = if (view != null) {
            view.tag as ItemUserBinding
        } else {
            createBinding(parent?.context)
        }

        return binding.root
    }

    private fun createBinding(context: Context?): ItemUserBinding {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(context))
        binding.imgDelete.setOnClickListener(this)
        binding.root.tag = binding

        return binding
    }

    override fun onClick(view: View?) {
        val user = view?.tag as User
        onDeleteListener.invoke(user)
    }
}
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

    override fun getItem(p0: Int): User {
        return users[p0]
    }

    override fun getItemId(p0: Int): Long {
        return users[p0].id
    }

    // Преобразование элементов данных в элементы списка
    // (для ListView)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val binding: ItemUserBinding = if (view != null) {
            view.tag as ItemUserBinding
        } else {
            createBinding(parent?.context)
        }

        val user: User = getItem(position)

        binding.tvName.text = user.name
        binding.imgDelete.tag = user
        binding.imgDelete.visibility = if (user.isCustom) View.VISIBLE else View.GONE

        return binding.root
    }

    /*// Преобразование элементов данных в элементы списка
    // (для Spinner)
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        return getDefaultItem(position, view, parent)
    }

    // При использовании Spinner
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getDefaultItem(position, convertView, parent, isDropDownView = true)
    }

    // При использовании Spinner
    private fun getDefaultItem(position: Int, view: View?, parent: ViewGroup?, isDropDownView: Boolean = false): View {
        val binding: ItemUserBinding = if (view != null) {
            view.tag as ItemUserBinding
        } else {
            createBinding(parent?.context)
        }

        val user: User = getItem(position)

        binding.tvName.text = user.name
        binding.imgDelete.tag = user
        binding.imgDelete.visibility = if (isDropDownView) View.VISIBLE else View.GONE

        return binding.root
    }*/

    override fun onClick(view: View?) {
        val user = view?.tag as User
        onDeleteListener.invoke(user)
    }

    private fun createBinding(context: Context?): ItemUserBinding {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(context))
        binding.imgDelete.setOnClickListener(this)
        binding.root.tag = binding

        return binding
    }
}
package com.example.nav_drawer.ui.list.baseadapter

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.nav_drawer.R
import com.example.nav_drawer.databinding.DialogAddUserBinding
import com.example.nav_drawer.databinding.FragmentListBaseAdapterBinding
import com.example.nav_drawer.databinding.FragmentListBinding
import com.example.nav_drawer.ui.list.ListFragment
import kotlin.random.Random

class ListBaseAdapterFragment : Fragment() {
    private var _binding: FragmentListBaseAdapterBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListBaseAdapter

    private val data = mutableListOf(
        User(id = 1, name = "Владимир", isCustom = false),
        User(id = 2, name = "Алексей", isCustom = false),
        User(id = 3, name = "Константин", isCustom = false),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = FragmentListBaseAdapterBinding.inflate(inflater, container, false)

        initList()

        binding.btnAdd.setOnClickListener { addUser() }

        return binding.root
    }

    private fun initList() {
        adapter = ListBaseAdapter(data) {
            deleteUser(it)
        }

        binding.lvUsers.adapter = adapter

        // При использовании ListView использовать эту функцию
        binding.lvUsers.setOnItemClickListener { _, _, i, _ ->
            infoUser(adapter.getItem(i))
        }

        // При использовании Spinner использовать эту функцию
        /*binding.lvUsers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.tvInfo.text = ""
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val user = data[p2]
                binding.tvInfo.text = "id " + user.id.toString() + ",\nимя " + user.name
            }
        }*/
    }

    private fun addUser() {
        val dialogAddUser = DialogAddUserBinding.inflate(layoutInflater)
        val dialog: AlertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Добавить пользователя")
            .setView(dialogAddUser.root)
            .setPositiveButton("Добавить") { _, _ ->
                val name = dialogAddUser.inpUserName.text.toString()
                if (name.isNotBlank()) {
                    createUser(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createUser(name: String) {
        val user = User(
            id = Random.nextLong(),
            name = name,
            isCustom = true
        )

        data.add(user)

        adapter.notifyDataSetChanged()
    }

    private fun infoUser(user: User) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Пользователь")
            .setMessage("id ${user.id.toString()},\nимя ${user.name}")
            .setPositiveButton("OK") { _, _ -> }
            .create()
        dialog.show()

    }

    private fun deleteUser(user: User) {
        val listener = DialogInterface.OnClickListener { _, i ->
            if (i == DialogInterface.BUTTON_POSITIVE) {
                data.remove(user)
                adapter.notifyDataSetChanged()
            }
        }

        val dialog: AlertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Удаление пользователя")
            .setMessage("Вы действительно хотите удалить пользователя ${user.name}")
            .setPositiveButton("Удалить", listener)
            .setNegativeButton("Отмена", listener)
            .create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.example.nav_drawer.ui.list

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nav_drawer.R
import com.example.nav_drawer.databinding.DialogAddUserBinding
import com.example.nav_drawer.databinding.FragmentListBinding
import java.util.*

class ListFragment : Fragment() {
    lateinit var viewModel: ListViewModel
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        binding.rgAdapters.setOnCheckedChangeListener { radioGroup, _ ->
            binding.fabAdd.visibility = View.INVISIBLE

            when (radioGroup.checkedRadioButtonId) {
                R.id.simpleAdapter -> setupListViewSimple()
                R.id.arrayAdapter -> setupListViewArray()
            }
        }

        setupListViewSimple()

        return binding.root
    }

    private fun addUser(adapter: ArrayAdapter<User>) {
        val dialogAddUser = DialogAddUserBinding.inflate(layoutInflater)
        val dialog: AlertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Добавить пользователя")
            .setView(dialogAddUser.root)
            .setPositiveButton("Добавить") {_, _ ->
                val name = dialogAddUser.inpUserName.text.toString()
                if (name.isNotBlank()) {
                    createUser(name, adapter)
                }
            }
            .create()
        dialog.show()
    }

    private fun createUser(name: String, adapter: ArrayAdapter<User>) {
        val user = User(
            id = UUID.randomUUID().toString(),
            name = name
        )

        adapter.add(user)
    }

    private fun setupListViewArray() {
        binding.fabAdd.visibility = View.VISIBLE

        val data: MutableList<User> = mutableListOf(
            User(id = UUID.randomUUID().toString(), name = "Владимир"),
            User(id = UUID.randomUUID().toString(), name = "Алексей"),
            User(id = UUID.randomUUID().toString(), name = "Константин")
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            data
        )

        binding.fabAdd.setOnClickListener { addUser(adapter) }

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, i, _ ->
            adapter.getItem(i)?.let {
                deleteUser(it, adapter)
            }
        }
    }

    private fun deleteUser(user: ListFragment.User, adapter: ArrayAdapter<User>) {
        val listener = DialogInterface.OnClickListener { _, i ->
            if (i == DialogInterface.BUTTON_POSITIVE) {
                adapter.remove(user)
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

    private fun setupListViewSimple() {
        /*val data: List<Map<String, String>> = listOf(
            mapOf(
                KEY_TITLE to "Title 1",
                KEY_DESCRIPTION to "Description 1"
            ),
            mapOf(
                KEY_TITLE to "Title 2",
                KEY_DESCRIPTION to "Description 2"
            ),
            mapOf(
                KEY_TITLE to "Title 3",
                KEY_DESCRIPTION to "Description 3"
            ),
        )*/

        val data: List<Map<String, String>> = (1..100).map {
            mapOf(
                KEY_TITLE to "Title $it",
                KEY_DESCRIPTION to "Description $it"
            )
        }

        val adapter = SimpleAdapter(
            requireContext(),
            data,
            android.R.layout.simple_list_item_2,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )

        binding.listView.adapter = adapter

        /*binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            val title = data[i][KEY_TITLE]
            val description = data[i][KEY_DESCRIPTION]

            val dialog = AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(description)
                //.setPositiveButton("OK") {_, _ ->}
                .create()
            dialog.show()
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class User(
        val id: String,
        val name: String
    ) {
        override fun toString(): String {
            return "$name - (идентификатор $id)"
        }
    }

    companion object {
        @JvmStatic val KEY_TITLE ="title"
        @JvmStatic val KEY_DESCRIPTION ="description"
    }
}
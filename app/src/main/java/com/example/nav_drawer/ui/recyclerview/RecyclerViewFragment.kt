package com.example.nav_drawer.ui.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nav_drawer.MainActivity
import com.example.nav_drawer.databinding.FragmentRecyclerViewBinding
import com.example.nav_drawer.ui.recyclerview.model.User
import com.example.nav_drawer.ui.recyclerview.model.UsersListener
import com.example.nav_drawer.ui.recyclerview.model.UsersService

class RecyclerViewFragment : Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: RecyclerViewAdapter

    /*private val usersService: UsersService
        get() = (applicationContext as App).usersService*/
    private val usersService = UsersService()

    private val usersListener: UsersListener = {
        adapter.users = it
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        adapter = RecyclerViewAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                usersService.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                Toast.makeText(requireContext(), "Пользователь: ${user.name}", Toast.LENGTH_SHORT).show()
            }

        })

        val layoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        usersService.addListener(usersListener)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        usersService.removeListener(usersListener)
        _binding = null
    }
}
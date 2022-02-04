package com.example.nav_drawer.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nav_drawer.databinding.FragmentStateBinding

class StateFragment : Fragment() {
    private lateinit var viewModel: StateViewModel
    private var _binding: FragmentStateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[StateViewModel::class.java]

        _binding = FragmentStateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with (binding) {
            btnIncrement.setOnClickListener { viewModel.increment() }
            btnColor.setOnClickListener { viewModel.setColor() }
            btnVisible.setOnClickListener { viewModel.switchVisible() }
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })

        return root
    }

    private fun renderState(state: StateViewModel.State) {
        with (binding.tvValue) {
            text = state.value.toString()
            setTextColor(state.color)
            visibility = if (state.visible) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
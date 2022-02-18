package com.example.nav_drawer.ui.dialog_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.example.nav_drawer.databinding.FragmentStateBinding
import com.example.nav_drawer.databinding.FragmentTestDialogBinding

class TestDialogFragment : Fragment() {
    private var _binding: FragmentTestDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTestDialogBinding.inflate(inflater, container, false)

        binding.btnSimpleDialog.setOnClickListener { showSimpleDialog() }
        setupSimpleDialogListener()

        return binding.root
    }

    private fun setupSimpleDialogListener() {
        setFragmentResultListener(SimpleDialogFragment.REQUEST_KEY, )
    }

    private fun showSimpleDialog() {
        val dialog = SimpleDialogFragment()
        dialog.show(parentFragmentManager, SimpleDialogFragment.TAG)
    }
}
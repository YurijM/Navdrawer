package com.example.nav_drawer.ui.dialog_fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        parentFragmentManager.setFragmentResultListener(
            SimpleDialogFragment.REQUEST_KEY,
            viewLifecycleOwner,
            FragmentResultListener { _, result ->

                when (result.getInt(SimpleDialogFragment.RESPONSE_KEY)) {
                    DialogInterface.BUTTON_POSITIVE -> Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
                    DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(requireContext(), "No", Toast.LENGTH_SHORT).show()
                    DialogInterface.BUTTON_NEUTRAL -> Toast.makeText(requireContext(), "Ignore", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun showSimpleDialog() {
        val dialog = SimpleDialogFragment()
        dialog.show(parentFragmentManager, SimpleDialogFragment.TAG)
    }
}
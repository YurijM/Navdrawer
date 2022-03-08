package com.example.nav_drawer.ui.dialog_fragment

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.example.nav_drawer.R
import com.example.nav_drawer.databinding.FragmentTestDialogBinding
import kotlin.properties.Delegates.notNull

class TestDialogFragment : Fragment() {
    private var _binding: FragmentTestDialogBinding? = null
    private val binding get() = _binding!!
    private var volume by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTestDialogBinding.inflate(inflater, container, false)

        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 50
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.RED

        binding.btnSimpleDialog.setOnClickListener { showSimpleDialog() }
        setupSimpleDialogListener()

        binding.btnSingleChoiceDialog.setOnClickListener { showSingleChoiceDialog() }
        setupSingleChoiceDialogListener()

        binding.btnSingleChoiceConfirmDialog.setOnClickListener { showSingleChoiceConfirmDialog() }
        setupSingleChoiceConfirmDialogListener()

        binding.btnMultiChoiceDialog.setOnClickListener { showMultiChoiceDialog() }
        setupMultiChoiceDialogListener()

        binding.btnMultiChoiceConfirmDialog.setOnClickListener { showMultiChoiceConfirmDialog() }
        setupMultiChoiceConfirmDialogListener()

        updateUI()

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_COLOR, color)
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

    private fun setupSingleChoiceDialogListener() {
        SingleChoiceDialogFragment.setupListener(parentFragmentManager, viewLifecycleOwner) {
            volume = it
            updateUI()
        }
    }

    private fun showSingleChoiceDialog() {
        SingleChoiceDialogFragment.show(parentFragmentManager, volume)
    }

    private fun setupSingleChoiceConfirmDialogListener() {
        SingleChoiceConfirmDialogFragment.setupListener(parentFragmentManager, viewLifecycleOwner) {
            volume = it
            updateUI()
        }
    }

    private fun showSingleChoiceConfirmDialog() {
        SingleChoiceConfirmDialogFragment.show(parentFragmentManager, volume)
    }

    private fun setupMultiChoiceDialogListener() {
        MultiChoiceDialogFragment.setupListener(parentFragmentManager, viewLifecycleOwner) {
            color = it
            updateUI()
        }
    }

    private fun showMultiChoiceDialog() {
        MultiChoiceDialogFragment.show(parentFragmentManager, color)
    }

    private fun setupMultiChoiceConfirmDialogListener() {
        MultiChoiceConfirmDialogFragment.setupListener(parentFragmentManager, viewLifecycleOwner) {
            color = it
            updateUI()
        }
    }

    private fun showMultiChoiceConfirmDialog() {
        MultiChoiceConfirmDialogFragment.show(parentFragmentManager, color)
    }

    private fun updateUI() {
        binding.txtCurrentVolume.text = getString(R.string.current_volume, volume)
        binding.colorView.setBackgroundColor(color)
    }

    companion object {
        @JvmStatic private val KEY_VOLUME = "KEY_VOLUME"
        @JvmStatic private val KEY_COLOR = "KEY_COLOR"
    }
}
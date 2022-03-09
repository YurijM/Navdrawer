package com.example.nav_drawer.ui.dialog_fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.nav_drawer.R
import com.example.nav_drawer.databinding.CustomInputBinding
import com.example.nav_drawer.databinding.CustomVolumeBinding
import com.example.nav_drawer.ui.dialog_fragment.entities.AvailableVolumeValues

typealias CustomInputDialogListener = (requestKey: String, volume: Int) -> Unit

class CustomInputDialogFragment : DialogFragment() {
    private val volume: Int
        get() = requireArguments().getInt(ARG_VOLUME)

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_KEY)!!

    @SuppressLint("DialogFragmentCallbacksDetector")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = CustomInputBinding.inflate(layoutInflater)
        dialogBinding.edtVolume.setText(volume.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setView(dialogBinding.root)
            .setPositiveButton("Установить", null)
            .create()

        dialog.setOnShowListener {
            dialogBinding.edtVolume.requestFocus()
            showKeyboard(dialogBinding.edtVolume)

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText = dialogBinding.edtVolume.text.toString()
                if (enteredText.isBlank()) {
                    dialogBinding.edtVolume.error = "Пустая строка"
                    return@setOnClickListener
                }
                val volume = enteredText.toIntOrNull()
                if (volume == null || volume > 100) {
                    dialogBinding.edtVolume.error = "Неверное значение"
                    return@setOnClickListener
                }
                parentFragmentManager.setFragmentResult(requestKey, bundleOf(RESPONSE_KEY_VOLUME to volume))
                dismiss()
            }
        }
        dialog.setOnDismissListener { hideKeyboard(dialogBinding.edtVolume) }

        return dialog
    }

    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    companion object {
        @JvmStatic private val TAG = CustomInputDialogFragment::class.java.simpleName
        @JvmStatic private val RESPONSE_KEY_VOLUME = "RESPONSE_KEY_VOLUME"
        @JvmStatic private val ARG_VOLUME = "ARG_VOLUME"
        @JvmStatic private val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"

        //@JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, volume: Int, requestKey: String) {
            val dialogFragment = CustomInputDialogFragment()
            dialogFragment.arguments = bundleOf(
                ARG_VOLUME to volume,
                ARG_REQUEST_KEY to requestKey
            )
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, requestKey: String, listener: CustomInputDialogListener) {
            manager.setFragmentResultListener(requestKey, lifecycleOwner, FragmentResultListener { key, result ->
                listener.invoke(key, result.getInt(RESPONSE_KEY_VOLUME))
            })
        }

    }
}
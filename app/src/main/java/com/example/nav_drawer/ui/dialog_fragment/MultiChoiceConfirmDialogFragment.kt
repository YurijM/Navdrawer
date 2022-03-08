package com.example.nav_drawer.ui.dialog_fragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.nav_drawer.R
import com.example.nav_drawer.ui.dialog_fragment.entities.AvailableVolumeValues

class MultiChoiceConfirmDialogFragment : DialogFragment() {
    private val color: Int
        get() = requireArguments().getInt(ARG_COLOR)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val colorItems = resources.getStringArray(R.array.colors)
        val colorComponents = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )
        val checkboxes = colorComponents
            .map { it > 0 && savedInstanceState == null }
            .toBooleanArray()

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(colorItems, checkboxes, null)
            .setPositiveButton("Установить") { dialog, _ ->
                val checkedPositions = (dialog as AlertDialog).listView.checkedItemPositions
                val color = Color.rgb(
                    booleanToColorComponent(checkedPositions[0]),
                    booleanToColorComponent(checkedPositions[1]),
                    booleanToColorComponent(checkedPositions[2])
                )
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(RESPONSE_KEY_COLOR to color))
            }
            .create()
    }

    private fun booleanToColorComponent(value: Boolean): Int {
        return if (value) 255 else 0
    }

    companion object {
        @JvmStatic private val TAG = MultiChoiceConfirmDialogFragment::class.java.simpleName
        @JvmStatic private val RESPONSE_KEY_COLOR = "RESPONSE_KEY_COLOR"
        @JvmStatic private val ARG_COLOR = "ARG_COLOR"

        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, color: Int) {
            val dialogFragment = MultiChoiceConfirmDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_COLOR to color)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                listener.invoke(result.getInt(RESPONSE_KEY_COLOR))
            })
        }
    }
}
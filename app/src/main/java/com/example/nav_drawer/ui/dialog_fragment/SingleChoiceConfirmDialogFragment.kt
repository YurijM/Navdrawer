package com.example.nav_drawer.ui.dialog_fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.nav_drawer.R
import com.example.nav_drawer.ui.dialog_fragment.entities.AvailableVolumeValues

class SingleChoiceConfirmDialogFragment : DialogFragment() {
    private val volume: Int
        get() = requireArguments().getInt(ARG_VOLUME)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex, null)
            .setPositiveButton("Установить") { dialog, _ ->
                val index = (dialog as AlertDialog).listView.checkedItemPosition
                val volume = volumeItems.values[index]
                parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(RESPONSE_KEY_VOLUME to volume))
            }
            .create()
    }

    companion object {
        @JvmStatic private val TAG = SingleChoiceConfirmDialogFragment::class.java.simpleName
        @JvmStatic private val RESPONSE_KEY_VOLUME = "RESPONSE_KEY_VOLUME"
        @JvmStatic private val ARG_VOLUME = "ARG_VOLUME"

        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, volume: Int) {
            val dialogFragment = SingleChoiceConfirmDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_VOLUME to volume)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                listener.invoke(result.getInt(RESPONSE_KEY_VOLUME))
            })
        }
    }
}
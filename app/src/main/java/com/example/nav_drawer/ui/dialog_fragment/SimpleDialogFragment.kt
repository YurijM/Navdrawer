package com.example.nav_drawer.ui.dialog_fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.nav_drawer.R

class SimpleDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DialogInterface.OnClickListener { _, i ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(RESPONSE_KEY to i))
        }

        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle("Простой диалог")
            .setMessage("Вы действительно хотите выйти из диалога?")
            .setPositiveButton("Да", listener)
            .setNegativeButton("Нет", listener)
            .setNeutralButton("Не знаю", listener)
            .create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    companion object {
        @JvmStatic val TAG = SimpleDialogFragment::class.java.simpleName
        @JvmStatic val REQUEST_KEY = "$TAG:defaultRequestKey"
        @JvmStatic val RESPONSE_KEY = "RESPONSE"
    }
}
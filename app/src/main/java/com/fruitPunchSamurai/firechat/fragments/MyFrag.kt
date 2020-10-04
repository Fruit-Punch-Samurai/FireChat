package com.fruitPunchSamurai.firechat.fragments

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

open class MyFrag : Fragment() {

    fun showSnackBar(msg: String) {
        Snackbar.make(requireView(), msg, 2000).show()
    }

    fun showSnackBar(resID: Int) {
        val msg = getString(resID)
        showSnackBar(msg)
    }

    /**Shows a Snackbar with the message "getString([resID]) + [msg]" */
    fun showSnackBar(resID: Int, msg: String) {
        val finalMessage = getString(resID) + msg
        showSnackBar(finalMessage)
    }

    fun navigateTo(actionID: Int) {
        findNavController().navigate(actionID)
    }
}
package com.fruitPunchSamurai.firechat.others

import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

object MyFrag {

    fun Fragment.getApplication() {
        requireActivity().application
    }

    fun Fragment.showSnackBar(msg: String?) {
        Snackbar.make(requireView(), msg.toString(), 2000).show()
    }

    /**Shows a Snackbar with the message "[getString(resID)]" */
    fun Fragment.showSnackBar(@StringRes resID: Int) {
        val msg = getString(resID)
        showSnackBar(msg)
    }

    /**Shows a Snackbar with the message "[getString(resID)] + [msg]" */
    fun Fragment.showSnackBar(@StringRes resID: Int, msg: String) {
        val finalMessage = getString(resID) + msg
        showSnackBar(finalMessage)
    }

    fun Fragment.navigateTo(@IdRes actionID: Int) {
        findNavController().navigate(actionID)
    }

    fun Fragment.hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun Fragment.makeLayoutTouchable(touchable: Boolean) {
        if (touchable) requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        else requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

}

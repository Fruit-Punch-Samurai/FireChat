package com.fruitPunchSamurai.firechat.others

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

abstract class MyFrag : Fragment() {

    val application: Application
        get() = requireActivity().application

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initiateViewModel()
        val view = initiateDataBinder(container)
        bindData()
        return view
    }

    /**Used to initiate the ViewModel using[ViewModelProvider] instead of [by viewModels()]*/
    open fun initiateViewModel() {}

    /**Initiates the Data/View Binder and returns its root view*/
    abstract fun initiateDataBinder(container: ViewGroup?): View?

    /**Binds the DataBinderData*/
    open fun bindData() {}

    fun showSnackBar(msg: String) {
        Snackbar.make(requireView(), msg, 2000).show()
    }

    /**Shows a Snackbar with the message "[getString(resID)]" */
    fun showSnackBar(resID: Int) {
        val msg = getString(resID)
        showSnackBar(msg)
    }

    /**Shows a Snackbar with the message "[getString(resID)] + [msg]" */
    fun showSnackBar(resID: Int, msg: String) {
        val finalMessage = getString(resID) + msg
        showSnackBar(finalMessage)
    }

    fun navigateTo(actionID: Int) {
        findNavController().navigate(actionID)
    }

    fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun makeLayoutTouchable(touchable: Boolean) {
        if (touchable) requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        else requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}

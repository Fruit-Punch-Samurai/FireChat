package com.fruitPunchSamurai.firechat.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.FullImageFragmentBinding
import com.fruitPunchSamurai.firechat.fragments.ChatFrag
import com.fruitPunchSamurai.firechat.viewModels.FullImageViewModel

class FullImageFrag : DialogFragment() {

    companion object {
        fun newInstance() = ChatFrag()
    }

    private val vm: FullImageViewModel by viewModels()
    private var b: FullImageFragmentBinding? = null
    private val args: FullImageFragArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*Setting the Dialog to be FullSize */
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = initiateDataBinder(container)
        bindData()
        return view
    }

    private fun initiateDataBinder(container: ViewGroup?): View? {
        b = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.full_image_fragment,
            container,
            false
        ) as FullImageFragmentBinding
        return b?.root
    }

    private fun bindData() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListeners()
    }


    private fun setOnClickListeners() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

}
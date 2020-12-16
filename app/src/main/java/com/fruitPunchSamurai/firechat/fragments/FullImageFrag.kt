package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.FullImageFragmentBinding
import com.fruitPunchSamurai.firechat.ext.MyFrag.showSnackBar
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.viewModels.FullImageViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FullImageFrag : DialogFragment() {

    companion object {
        fun newInstance() = ChatFrag()
    }

    private val vm: FullImageViewModel by viewModels()
    private var b: FullImageFragmentBinding? = null
    private val args: FullImageFragArgs by navArgs()
    private lateinit var imageID: String
    private lateinit var receiverID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Setting the Dialog to be FullSize */
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
        b = FullImageFragmentBinding.inflate(layoutInflater, container, false)
        return b?.root
    }

    private fun bindData() {
        imageID = args.imageID
        receiverID = args.receiverID
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setOnClickListeners()
        observeState()
        setImage()
    }

    private fun observeState() {
        vm.state.observe(viewLifecycleOwner, {
            when (it) {
                is MyState.Error -> showSnackBar(it.msg)
                is MyState.Loading -> b?.floatingActionButton?.hide()
                is MyState.Finished -> {
                    showSnackBar(it.msg)
                    vm.setIdleState()
                }
                is MyState.Idle -> b?.floatingActionButton?.show()
            }
        })
    }

    private fun setOnClickListeners() {
        b?.floatingActionButton?.setOnClickListener { saveImage() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    private fun setImage() {
        MainScope().launch {
            val uri = vm.getImageURI(imageID, receiverID)
            if (b != null) Glide.with(this@FullImageFrag).load(uri).into(b!!.fullImage)
        }
    }

    private fun saveImage() {
        val bitmap = b?.fullImage?.drawable?.toBitmap() ?: return
        vm.saveImageToStorage(bitmap)
    }


}
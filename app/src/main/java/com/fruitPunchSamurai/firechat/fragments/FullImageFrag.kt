package com.fruitPunchSamurai.firechat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.FullImageFragmentBinding
import com.fruitPunchSamurai.firechat.ext.MyFrag.showSnackBar
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.viewModels.FullImageViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class FullImageFrag : DialogFragment() {

    private val vm: FullImageViewModel by viewModels()
    private var b: FullImageFragmentBinding? = null
    private val args: FullImageFragArgs by navArgs()

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
        vm.imageID = args.imageID
        vm.receiverID = args.receiverID
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
                is MyState.Idle -> b?.floatingActionButton?.show()
                is MyState.Loading -> b?.floatingActionButton?.hide()
                is MyState.Finished -> {
                    showSnackBar(it.msg)
                    vm.setIdleState()
                }
                is MyState.Error -> {
                    showSnackBar(it.msg);vm.setIdleState()
                }
            }
        })
    }

    private fun setOnClickListeners() {
        b?.floatingActionButton?.setOnClickListener { downloadImage() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        MainScope().cancel()
        b = null
    }

    private fun setImage() {
        MainScope().launch {
            val bitmap = vm.getImage(requireContext())
            if (b != null && bitmap != null) b!!.fullImage.setImageBitmap(bitmap)
        }
    }

    private fun downloadImage() {
        GlobalScope.launch {
            val bitmap = vm.getImage(requireContext())
            vm.saveImageToStorage(bitmap)
        }
    }


}
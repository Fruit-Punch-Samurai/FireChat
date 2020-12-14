package com.fruitPunchSamurai.firechat.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.fruitPunchSamurai.firechat.R
import com.fruitPunchSamurai.firechat.databinding.ChatFragmentBinding
import com.fruitPunchSamurai.firechat.databinding.ChatRecyclerBinding
import com.fruitPunchSamurai.firechat.ext.MyFrag.showSnackBar
import com.fruitPunchSamurai.firechat.models.Message
import com.fruitPunchSamurai.firechat.others.DateConverter
import com.fruitPunchSamurai.firechat.others.MyState
import com.fruitPunchSamurai.firechat.others.RecyclerOptions
import com.fruitPunchSamurai.firechat.repos.AuthRepo
import com.fruitPunchSamurai.firechat.repos.MainRepo
import com.fruitPunchSamurai.firechat.viewModels.ChatViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ChatFrag : DialogFragment() {

    companion object {
        fun newInstance() = ChatFrag()
    }

    private val vm: ChatViewModel by viewModels()
    private var b: ChatFragmentBinding? = null
    private val args: ChatFragArgs by navArgs()

    private lateinit var receiverID: String
    lateinit var receiverName: String
    private val imageRequestCode = 1
    lateinit var adapter: FirestoreRecyclerAdapter<Message, Holder>

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
            R.layout.chat_fragment,
            container,
            false
        ) as ChatFragmentBinding
        return b?.root
    }

    private fun bindData() {
        receiverID = args.receiverID
        receiverName = args.receiverName

        initiateRecyclerView()
        initiateAdapter(receiverID)

        b?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = vm
            frag = this@ChatFrag
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeState()
        setOnClickListeners()
    }

    private fun observeState() {
        vm.state.observe(viewLifecycleOwner, {
            when (it) {
                is MyState.Error -> {
                    showSnackBar(it.msg)
                }
                is MyState.Finished -> {
                    b?.recycler?.smoothScrollToPosition(adapter.itemCount)
                    vm.setIdleState()
                }
                else -> return@observe
            }
        })
    }

    private fun setOnClickListeners() {
        b?.newMessage?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND && !v.text.isNullOrBlank()) {
                sendMessage()
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        b = null
    }

    fun goBack() {
        findNavController().popBackStack()
    }

    fun chooseImage() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == imageRequestCode && resultCode == Activity.RESULT_OK && data != null) {
            GlobalScope.launch {
                val uri = data.data ?: return@launch
                vm.sendImageMessage(receiverID, receiverName, uri)
            }
        }
    }

    fun sendMessage() {
        GlobalScope.launch { vm.sendTextMessage(receiverID, receiverName) }
    }

    private fun initiateRecyclerView() {
        val man = LinearLayoutManager(requireActivity()).apply {
            stackFromEnd = true
        }
        b?.recycler?.layoutManager = man
    }

    private fun showDateSnackBar(binding: ChatRecyclerBinding) {
        val message = binding.message ?: return
        val rawDate = message.date
        val date = DateConverter().extractDate(rawDate)
        val time = DateConverter().extractTime(rawDate)

        showSnackBar("${getString(R.string.messageSentOn)} $date ${getString(R.string.at)} $time")

    }

    private fun initiateAdapter(receiverID: String) {
        adapter = object : FirestoreRecyclerAdapter<Message, Holder>(
            RecyclerOptions.getMessagesOption(viewLifecycleOwner, vm.getCurrentUserID(), receiverID)
        ) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val binding = ChatRecyclerBinding.inflate(LayoutInflater.from(parent.context))
                return Holder(binding) { showDateSnackBar(binding) }
            }

            override fun onBindViewHolder(holder: Holder, position: Int, model: Message) {
                holder.bindData(model, receiverID)
            }
        }
    }

    class Holder(private val b: ChatRecyclerBinding, val clickFun: () -> Unit) :
        RecyclerView.ViewHolder(b.root),
        View.OnLongClickListener {

        fun bindData(message: Message, receiverID: String) {
            b.message = message
            b.textView.setOnLongClickListener(this)
            b.textView2.setOnLongClickListener(this)
            b.executePendingBindings()

            setImage(message, receiverID)
        }

        private fun setImage(message: Message, receiverID: String) {
            b.imageView.run {
                visibility =
                    if (message.ownerID == AuthRepo().getUID() && message.typeIsImage()) {
                        MainScope().launch {
                            val uri =
                                MainRepo().getImage(
                                    message.mediaID,
                                    AuthRepo().getUID()!!,
                                    receiverID
                                )
                            Glide.with(this@run).load(uri).into(this@run)
                        }
                        View.VISIBLE
                    } else View.GONE
            }

            b.imageView2.run {
                visibility =
                    if (message.ownerID != AuthRepo().getUID() && message.typeIsImage()) {
                        MainScope().launch {
                            val uri =
                                MainRepo().getImage(
                                    message.mediaID,
                                    AuthRepo().getUID()!!,
                                    receiverID
                                )
                            Glide.with(this@run).load(uri).into(this@run)
                        }
                        View.VISIBLE
                    } else View.GONE
            }
        }

        override fun onLongClick(v: View?): Boolean {
            clickFun()
            return true
        }
    }
}
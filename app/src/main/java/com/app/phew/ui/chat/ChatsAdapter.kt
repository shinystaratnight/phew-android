package com.app.phew.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.listners.OnNotificationsListener
import com.app.phew.models.chats.ChatsModel
import com.app.phew.models.notifications.NotificationsModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_messages.view.*

class ChatsAdapter(
    private val context: Context, data: ArrayList<ChatsModel>,
    layoutId: Int, private val mListener: OnChatClickListener
) : ParentRecyclerAdapter<ChatsModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView

        Glide.with(context).load(model.other_user_data?.profile_image).into(myView.imvClientImage)
        myView.tvClientName.text = model.other_user_data.fullname
        myView.tvMessageDate.text = model.ago_time
        myView.tvLastMessage.text = model.last_message
        myView.setOnClickListener {
            mListener.onChatClick(position,model)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }

    interface OnChatClickListener {
        fun onChatClick(position: Int, model: ChatsModel)
    }
}
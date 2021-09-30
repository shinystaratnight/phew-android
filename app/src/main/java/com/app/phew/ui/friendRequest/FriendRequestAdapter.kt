package com.app.phew.ui.friendRequest

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.listners.OnNotificationsListener
import com.app.phew.models.friendRquestes.FriendRequestesModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_friend_requestes.view.*

class FriendRequestAdapter(
    private val context: Context, data: ArrayList<FriendRequestesModel>,
    layoutId: Int, private val mListener: OnNotificationsListener
) : ParentRecyclerAdapter<FriendRequestesModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView
        Glide.with(context).load(model.user.profile_image).into(myView.imvClientImage)
        myView.tvDate.text = model.date
        myView.tvTitle.text = context.resources.getString(R.string.dd)+" "+
                model.user.fullname+" "+context.resources.getString(R.string.sent_you_friend_request)
        myView.tvAccept.setOnClickListener {
            mListener.onAcceptClick(position,model.user.id?:1,"")
        }
        myView.tvCancel.setOnClickListener {
            mListener.onRejectClick(position,model.user.id?:1,"")
        }

    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }
}
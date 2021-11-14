package com.app.phew.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.auth.UserModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_post_friends.view.*
import kotlin.collections.ArrayList

class MentionFriendsAdapter(
    private val context: Context, data: ArrayList<UserModel>, private val mListener: onFriendClickListener
) : ParentRecyclerAdapter<UserModel>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post_friends, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemView = holder.itemView
        val itemData = data[position]

        itemView.cbPostFriendsItemCheck.visibility = View.GONE
        Glide.with(context).load(itemData.profile_image).placeholder(R.drawable.ic_anonymous).into(itemView.ivPostFriendsItemImage)
        itemView.tvPostFriendsItemName.text = itemData.fullname
        itemView.setOnClickListener { _ ->
            mListener.onFriendClick(position, itemData)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)

    interface onFriendClickListener {
        fun onFriendClick(position: Int, model: UserModel)
    }
}
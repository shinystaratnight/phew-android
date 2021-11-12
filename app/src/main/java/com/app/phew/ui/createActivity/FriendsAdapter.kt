package com.app.phew.ui.createActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.friends.FriendModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_post_friends.view.*
import kotlin.collections.ArrayList

class FriendsAdapter(
        private val context: Context, data: ArrayList<FriendModel>, private val mListener: onFriendCheckedChangeListener
) : ParentRecyclerAdapter<FriendModel>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post_friends, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemView = holder.itemView
        val itemData = data[position]

        Glide.with(context).load(itemData.user?.profile_image).into(itemView.ivPostFriendsItemImage)
        itemView.tvPostFriendsItemName.text = itemData.user?.fullname
        itemView.cbPostFriendsItemCheck.setOnCheckedChangeListener { _, checkedStatus ->
            mListener.onFriendClick(position, checkedStatus, itemData)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)

    interface onFriendCheckedChangeListener {
        fun onFriendClick(position: Int, checkedStatus: Boolean, model: FriendModel)
    }
}
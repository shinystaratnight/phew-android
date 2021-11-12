package com.app.phew.ui.createActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.friends.FriendModel
import kotlinx.android.synthetic.main.recycler_item_create_activity_friends.view.*
import kotlin.collections.ArrayList

class FriendNameAdapter(
        private val context: Context, data: ArrayList<FriendModel>, private val mListener: onFriendNameDeleteListener
) : ParentRecyclerAdapter<FriendModel>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_create_activity_friends, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemView = holder.itemView
        val itemData = data[position]

        itemView.tvActivityFriendsItemName.text = itemData.user?.fullname

        itemView.ibActivityFriendsItemDelete.setOnClickListener {
            mListener.onFriendDelete(position, itemData)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)

    interface onFriendNameDeleteListener {
        fun onFriendDelete(position: Int, model: FriendModel)
    }
}
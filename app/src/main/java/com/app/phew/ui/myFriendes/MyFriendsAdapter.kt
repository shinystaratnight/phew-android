package com.app.phew.ui.myFriendes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.friends.FriendModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_my_friends.view.*


class MyFriendsAdapter(
    private val context: Context, data: ArrayList<FriendModel>,
    layoutId: Int, private val mListener: OnDeleteClick
) : ParentRecyclerAdapter<FriendModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView
        Glide.with(context).load(model.user!!.profile_image).into(myView.imvClientImage)
        myView.tvClientName.text = model.user.fullname
        myView.tvDate.text = model.date
        myView.tvDelete.setOnClickListener {
            mListener.onDeleteClick(position,model)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }
    interface OnDeleteClick{
        fun onDeleteClick(position: Int,model:FriendModel)
    }
}
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
import kotlinx.android.synthetic.main.recycler_item_home_activity_mentions.view.*

/**
 * Created by Mohamed Balsha on 2/28/2021.
 */
class HomeActivityMentionsAdapter(context: Context, dataList: ArrayList<UserModel>) :
        ParentRecyclerAdapter<UserModel>(context, dataList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeActivityMentionsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home_activity_mentions, parent, false)
    )

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemData = data[position]
        val itemView = holder.itemView
        Glide.with(itemView.context).load(itemData.profile_image.toString())
                .placeholder(R.drawable.ic_emoji).into(itemView.ivActivityMentionsItemImage)
    }

    class HomeActivityMentionsViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)
}
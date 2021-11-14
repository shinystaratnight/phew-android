package com.app.phew.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.auth.UserModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_home_activity_mentions.view.*

/**
 * Created by Mohamed Balsha on 2/28/2021.
 */
class HomeActivityMentionsAdapter(context: Context, dataList: ArrayList<UserModel>, private val mListener: onMentionClickListener) :
        ParentRecyclerAdapter<UserModel>(context, dataList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeActivityMentionsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home_activity_mentions, parent, false)
    )

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemData = data[position]
        val itemView = holder.itemView
        if (itemData.username?.equals("mentionsExtraCount") == true) {
            itemView.ivActivityMentionsItemImage.visibility = View.GONE
            itemView.tvActivityMentionsItemText.visibility = View.VISIBLE
            itemView.tvActivityMentionsItemText.text = String.format("+%d", itemData.friends_count)
            val marginLayoutParams =
                MarginLayoutParams(itemView.linActivityMentionsItemContainer.layoutParams)
            marginLayoutParams.marginEnd = 0
            itemView.linActivityMentionsItemContainer.layoutParams = marginLayoutParams
        } else {
            if (position == data.size -1) {
                val marginLayoutParams = MarginLayoutParams(itemView.linActivityMentionsItemContainer.layoutParams)
                marginLayoutParams.marginEnd = 0
                itemView.linActivityMentionsItemContainer.layoutParams = marginLayoutParams
            }
            Glide.with(itemView.context).load(itemData.profile_image.toString())
                .placeholder(R.drawable.ic_emoji).into(itemView.ivActivityMentionsItemImage)
        }
        itemView.setOnClickListener {
            mListener.onMentionsClick()
        }
    }

    interface onMentionClickListener {
        fun onMentionsClick()
    }

    class HomeActivityMentionsViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)
}
package com.app.phew.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.images.ImageModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_post_attachments.view.*

/**
 * Created by Mohamed Balsha on 3/2/2021.
 */

class PostAttachmentsAdapter(context: Context, dataList: ArrayList<ImageModel>, var mListener: OnAttachmentListener) :
        ParentRecyclerAdapter<ImageModel>(context, dataList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostAttachmentsViewHolder(
            LayoutInflater.from(mcontext)
                    .inflate(R.layout.recycler_item_post_attachments, parent, false)
    )

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemView = holder.itemView
        val itemData = data[position]

        Glide.with(mcontext).load(itemData.data).into(itemView.imvAttachImage)
        itemView.imvAttachVideo.visibility = if (itemData.type == "video") View.VISIBLE else View.GONE
        itemView.setOnClickListener { mListener.onAttachmentListener() }
    }

    class PostAttachmentsViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)
    interface OnAttachmentListener {
        fun onAttachmentListener()
    }
}
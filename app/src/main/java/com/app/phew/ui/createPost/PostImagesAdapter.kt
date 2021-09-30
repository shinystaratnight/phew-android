package com.app.phew.ui.createPost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recycler_item_create_post_images.view.*

import kotlin.collections.ArrayList

class PostImagesAdapter(
        private val context: Context, data: ArrayList<String>,
        private val mListener: PostImageDeleteListener
) : ParentRecyclerAdapter<String>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_create_post_images, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemView = holder.itemView
        val itemData = data[position]

        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.color.colorPrimary)
        requestOptions.error(R.color.colorPrimary)
        Glide.with(context).load(itemData).apply(requestOptions)
                .thumbnail(Glide.with(context).load(itemData))
                .into(itemView.ivPostImagesItemImage)
        itemView.ibPostImagesItemDelete.setOnClickListener {
            mListener.onDeleteImageClick(position)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)

    override fun getItemCount() = data.size

    interface PostImageDeleteListener {
        fun onDeleteImageClick(position: Int)
    }
}
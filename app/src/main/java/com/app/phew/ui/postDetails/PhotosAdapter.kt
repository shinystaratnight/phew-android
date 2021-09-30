package com.app.phew.ui.postDetails

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.comment.CommentModel
import com.app.phew.models.images.ImageModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_recycler_show_images.view.*
import kotlinx.android.synthetic.main.recycler_item_create_post_images.view.*

class PhotosAdapter(
        private val context: Context, data: ArrayList<ImageModel>,
        layoutId: Int , private val mListener:OnPhotoClick
) : ParentRecyclerAdapter<ImageModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView


        if (model.type=="image"){
            myView.imvVideo.visibility = View.GONE
            Glide.with(context).load(model.data).into(myView.ivPostImage)
        }else{
            myView.imvVideo.visibility = View.VISIBLE
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.color.colorPrimary)
            requestOptions.error(R.color.colorPrimary)
            Glide.with(context).load(model.data).apply(requestOptions)
                    .thumbnail(Glide.with(context).load(model.data))
                    .into(myView.ivPostImage)
        }
        myView.ivPostImage.setOnClickListener {
            if (model.type=="video") mListener.onPhotoClick(position, model)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }
    interface OnPhotoClick{
        fun onPhotoClick(position: Int,model:ImageModel)
    }
}
package com.app.phew.ui.postDetails

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.comment.CommentModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_comment.view.*

class CommentsAdapter(
        private val context: Context, data: ArrayList<CommentModel>,
        layoutId: Int, private val mListener: OnCommentClick
) : ParentRecyclerAdapter<CommentModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView

        Glide.with(context).load(model.user.profile_image).into(myView.imvClientImage)
        myView.tvUserName.text = model.user.fullname
        myView.tvComment.text = model.text
        if (!model.images.isNullOrEmpty()){
            myView.imvComment.visibility = View.VISIBLE
            //myView.imvCommentMask
            if (model.images.size>1){
                myView.imvCommentMask.visibility = View.VISIBLE
                myView.imvCommentMask.text ="+"+ (model.images.size - 1).toString()
            }else{
                myView.imvCommentMask.visibility = View.GONE
            }
            myView.imvComment.visibility = View.VISIBLE
            Glide.with(context).load(model.images[0].data).into(myView.imvComment)
        }else{
            myView.imvCommentMask.visibility = View.GONE
            myView.imvComment.visibility = View.GONE
        }
        myView.imvCommentMask.setOnClickListener {
            mListener.onCommentClick(position,model)
        }
        myView.imvComment.setOnClickListener {
            mListener.onCommentClick(position,model)
        }

    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }
    interface OnCommentClick{
        fun onCommentClick(position: Int,model:CommentModel)
    }
}
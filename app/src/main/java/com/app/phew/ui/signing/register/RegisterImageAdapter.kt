package com.app.phew.ui.signing.register

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.listners.OnDeleteImageClickListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*

import kotlin.collections.ArrayList

class RegisterImageAdapter(
    private val context: Context, data: ArrayList<String>,
    layoutId: Int, private val mListener: OnDeleteImageClickListener
) : ParentRecyclerAdapter<String>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]

        Glide.with(context).load(model).placeholder(R.color.colorAccent)
            .into(viewHolder.itemView.imvMyImage)
        viewHolder.itemView.imvCancel.setOnClickListener {
            mListener.onDeleteImageClick(position)
        }

    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }
}
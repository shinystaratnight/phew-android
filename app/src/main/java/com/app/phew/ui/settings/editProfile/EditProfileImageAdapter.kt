package com.app.phew.ui.settings.editProfile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.images.ImageModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image.view.*

class EditProfileImageAdapter(
    private val context: Context, data: ArrayList<ImageModel>,
    layoutId: Int, private val mListener: OnDeleteClick
) : ParentRecyclerAdapter<ImageModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]

        Glide.with(context).load(model.image).placeholder(R.color.colorAccent)
            .into(viewHolder.itemView.imvMyImage)
        viewHolder.itemView.imvCancel.setOnClickListener {
            mListener.onDeleteClick(position,model)
        }

    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }
    interface OnDeleteClick{
        fun onDeleteClick(position: Int,model: ImageModel)
    }
}

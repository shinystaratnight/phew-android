package com.app.phew.ui.secretMessages

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.secretMessages.SecretMessageModel
import kotlinx.android.synthetic.main.recycler_item_secret_messages.view.*

class SecretMessagesAdapter(
    private val context: Context, data: ArrayList<SecretMessageModel>,
    layoutId: Int, private val mListener: OnSecretMessageClickListener
) : ParentRecyclerAdapter<SecretMessageModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView

        myView.tvClientName.text = context.resources.getString(R.string.message)+" #"+ model.id
        myView.tvMessageDate.text = model.ago_time
        myView.tvLastMessage.text = model.message
        myView.setOnClickListener {
            mListener.onSecretMessageClick(position,model)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }

    interface OnSecretMessageClickListener {
        fun onSecretMessageClick(position: Int, model: SecretMessageModel)
    }
}
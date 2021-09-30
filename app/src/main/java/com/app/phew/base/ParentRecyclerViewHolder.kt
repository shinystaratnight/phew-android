package com.app.phew.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.phew.listners.OnItemClickListener


/**
 * is a base class to extend from it the viewholder
 */
open class ParentRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var clickableRootView: View? = null // this is used to change the default onItemClickListener

    init {

    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        if (clickableRootView != null) {
            clickableRootView!!.setOnClickListener { v ->
                itemClickListener?.onItemClick(v, adapterPosition)
            }
        } else {
            itemView.setOnClickListener { v ->
                itemClickListener?.onItemClick(v, adapterPosition)
            }
        }
    }

    fun setClickableRootView(clickableRootView: View) {
        this.clickableRootView = clickableRootView
    }

    fun findViewById(viewId: Int): View? {
        return itemView.findViewById(viewId)
    }
}

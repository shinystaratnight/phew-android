package com.app.phew.utils.views

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.ListModel

class ListDialogsAdapter(context: Context, data: ArrayList<ListModel>) :
    ParentRecyclerAdapter<ListModel>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var viewHolder: ParentRecyclerViewHolder?
        val viewItem = inflater.inflate(R.layout.recycler_dialog_list_row, parent, false)
        viewHolder = ListAdapter(viewItem)

        return viewHolder
    }


    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val listAdapter = holder as ListAdapter
        val listModel = data[position]
        if (listModel.name != null) {
            listAdapter.tv_row_title.text = listModel.name
        } else {
            listAdapter.tv_row_title.text = listModel.name

        }
        if (position % 2 == 0) {
            listAdapter.tv_row_title.setBackgroundColor(Color.WHITE)
        } else {
            listAdapter.tv_row_title.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.colorAccent))

        }

        listAdapter.tv_row_title.setOnClickListener { view -> itemClickListener.onItemClick(view, position) }
    }


    protected inner class ListAdapter(itemView: View) : ParentRecyclerViewHolder(itemView) {

        var tv_row_title: TextView

        init {
            setClickableRootView(itemView)
            tv_row_title = itemView.findViewById(R.id.tv_row_title)
        }
    }
}

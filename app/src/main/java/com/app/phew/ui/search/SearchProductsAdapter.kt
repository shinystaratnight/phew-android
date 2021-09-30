package com.app.phew.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.listners.OnItemClickListener
import com.app.phew.models.searchResponse.SearchModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_search.view.*


class SearchProductsAdapter(
        context: Context, dataList: ArrayList<SearchModel>, private val mListener: OnItemClickListener
) : ParentRecyclerAdapter<SearchModel>(context, dataList) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BranchesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_search, parent, false)
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val user = data[position]
        val myView = holder.itemView
        Glide.with(myView.context).load(user.profile_image).into(myView.imvClientImage)
        myView.tvClientName.text = user.fullname
        holder.setOnItemClickListener(mListener)
    }

    class BranchesViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)
}
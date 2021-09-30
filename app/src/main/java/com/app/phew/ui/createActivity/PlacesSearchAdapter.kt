package com.app.phew.ui.createActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.places.MapsSearchData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_places.view.*
import kotlin.collections.ArrayList

class PlacesSearchAdapter(
        private val context: Context, data: ArrayList<MapsSearchData>, private val mListener: OnPlaceSearchClickListener
) : ParentRecyclerAdapter<MapsSearchData>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_places, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemView = holder.itemView
        val itemData = data[position]

        Glide.with(context).load(itemData.icon.toString()).into(itemView.ivLocationsItemImage)
        itemView.tvLocationsItemName.text = itemData.name
        itemView.tvLocationsItemVotes.text = itemData.formattedAddress

        itemView.setOnClickListener { mListener.onPlaceSearchClicked(position) }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)

    interface OnPlaceSearchClickListener {
        fun onPlaceSearchClicked(position: Int)
    }
}
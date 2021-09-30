package com.app.phew.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.findly.findlyCities.FindlyCity
import kotlinx.android.synthetic.main.recycler_item_findly_countries.view.*

class FindlyCitiesAdapter(
    private val context: Context, data: ArrayList<FindlyCity>,
    layoutId: Int, private val mListener: OnCityClick
) : ParentRecyclerAdapter<FindlyCity>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView

        myView.tvCityName.text = model.name
        myView.tvLikeCount.text = model.like_count.toString()
        when(model.like_type){
            "love"->{
                myView.imvLikeType.background = ContextCompat.getDrawable(context,R.drawable.ic_react_love_on)
            }
            "laugh"->{
                myView.imvLikeType.background = ContextCompat.getDrawable(context,R.drawable.ic_react_laugh_on)
            }
            "dislike"->{myView.imvLikeType.background = ContextCompat.getDrawable(context,R.drawable.ic_react_dislike_on)}
            else->{
                myView.imvLikeType.background = ContextCompat.getDrawable(context,R.drawable.ic_react_laugh_on)
            }
        }
        myView.setOnClickListener {
            mListener.onCityClick(position,model)
        }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }

    interface OnCityClick {
        fun onCityClick(position: Int, model: FindlyCity)
    }
}
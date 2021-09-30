package com.app.phew.ui.createActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.movies.MovieDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_movies.view.*
import kotlin.collections.ArrayList

class MoviesSearchAdapter(
        private val context: Context, data: ArrayList<MovieDetail>, private val mListener: OnMovieSearchClickListener
) : ParentRecyclerAdapter<MovieDetail>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_movies, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemView = holder.itemView
        val itemData = data[position]

        val logo = "https://image.tmdb.org/t/p/original/" + if (!itemData.logoImage.isNullOrEmpty())
            itemData.logoImage else itemData.poster_path
        Glide.with(context).load(logo).into(itemView.ivMoviesItemImage)
        itemView.tvMoviesItemName.text = itemData.title
        itemView.tvMoviesItemVotes.text = String.format(
                "%d %s", itemData.voteCount ?: itemData.vote_count,
                context.getString(R.string.voted_on_this_view)
        )

        itemView.setOnClickListener { mListener.onMovieSearchClicked(position) }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)

    interface OnMovieSearchClickListener {
        fun onMovieSearchClicked(position: Int)
    }
}
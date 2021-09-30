package com.app.phew.ui.createActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.movies.MovieModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_movies.view.*
import kotlin.collections.ArrayList

class MoviesAdapter(
        private val context: Context, data: ArrayList<MovieModel>, private val mListener: OnMovieClickListener
) : ParentRecyclerAdapter<MovieModel>(context, data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_movies, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemView = holder.itemView
        val itemData = data[position]

        val logo = "https://image.tmdb.org/t/p/original/" +
                if (!itemData.movie_detail?.logoImage.isNullOrEmpty()) itemData.movie_detail?.logoImage
                else itemData.movie_detail?.poster_path
        Glide.with(context).load(logo).into(itemView.ivMoviesItemImage)
        itemView.tvMoviesItemName.text = itemData.movie_detail?.title
        itemView.tvMoviesItemVotes.text = String.format(
                "%d %s",
                itemData.movie_detail?.voteCount ?: itemData.movie_detail?.vote_count,
                context.getString(R.string.voted_on_this_view)
        )

        itemView.setOnClickListener { mListener.onMovieClicked(position) }
    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)

    interface OnMovieClickListener {
        fun onMovieClicked(position: Int)
    }
}
package com.app.phew.listners

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerOnScrollListener(private val mLinearLayoutManager: StaggeredGridLayoutManager, private val recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0
    internal var first: IntArray? = null
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 1 // The minimum amount of items to have below your current scroll position before loading more.
    private var current_page = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        val firstCompletelyVisibleItemPositions = mLinearLayoutManager.findFirstCompletelyVisibleItemPositions(first)
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstCompletelyVisibleItemPositions[0] + visibleThreshold) {
            // End has been reached

            // Do something
            current_page++
            onLoadMore(current_page)
            loading = true
        }
    }

    abstract fun onLoadMore(current_page: Int)

    companion object {
        var TAG = EndlessRecyclerOnScrollListener::class.java.simpleName
    }
}
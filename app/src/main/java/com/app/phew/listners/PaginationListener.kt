package com.app.phew.listners

import android.nfc.tech.MifareUltralight
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Mohamed Balsha on 2/28/2021.
 */

abstract class PaginationListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    companion object {
        const val PAGE_START = 1
        private const val PAGE_SIZE = 10
    }

    var myDy = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE
            )
                loadMoreItems()
        }

        myDy = dy
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (myDy > 0) scrollToDown()
        else scrollToUp()
    }

    protected abstract fun loadMoreItems()
    protected abstract fun scrollToDown()
    protected abstract fun scrollToUp()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}
package com.app.phew.listners

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.phew.utils.CommonUtil


/**
 * Pagination
 * Created by Suleiman19 on 10/15/16.
 * Copyright (c) 2016. Suleiman Ali Shakir. All rights reserved.
 */
abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract val totalPageCount: Int
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        CommonUtil.PrintLogE(
            "visibleItemCount : " + visibleItemCount + "  totalItemCount : " + totalItemCount
                    + "firstVisibleItemPosition :" + firstVisibleItemPosition
        )

        if (!isLoading && !isLastPage)
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                CommonUtil.PrintLogE("Scroll")
                loadMoreItems()
            } else CommonUtil.PrintLogE("Not Scroll")
    }

    protected abstract fun loadMoreItems()
}
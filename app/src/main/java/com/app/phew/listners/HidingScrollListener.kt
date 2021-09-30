package com.app.phew.listners


import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
* This class is a ScrollListener for RecyclerView that allows to show/hide
* views when list is scrolled. It assumes that you have added a header
* to your list. @see pl.michalz.hideonscrollexample.adapter.partone.RecyclerAdapter
* */
abstract class HidingScrollListener : RecyclerView.OnScrollListener() {

    private var mScrolledDistance = 0
    private var mControlsVisible = true


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (firstVisibleItem == 0) {
            if (!mControlsVisible) {
                onShow()
                mControlsVisible = true
            }
        } else {
            if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                onHide()
                mControlsVisible = false
                mScrolledDistance = 0
            } else if (mScrolledDistance < HIDE_THRESHOLD && !mControlsVisible) {
                onShow()
                mControlsVisible = true
                mScrolledDistance = 0
            }
        }
        if (mControlsVisible && dy > 0 || !mControlsVisible && dy < 0) {
            mScrolledDistance += dy
        }
    }

    abstract fun onHide()

    abstract fun onShow()

    companion object {

        private val HIDE_THRESHOLD = 20
    }
}
package com.app.phew.listners

import android.view.View

interface OnItemClickListener {
    fun onItemClick(view: View, position: Int)
}

interface OnNotificationsListener {
    fun onAcceptClick( position: Int,userId:Int,notificationId:String)
    fun onRejectClick( position: Int,userId:Int,notificationId:String)
}

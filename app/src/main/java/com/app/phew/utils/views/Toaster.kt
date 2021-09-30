package com.app.phew.utils.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.app.phew.R


class Toaster(internal var mContext: Context) : Toast(mContext) {

    fun makeToast(message: String) {
        val inflater = mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.toast_custom_layout, null)
        val tv_toast = view.findViewById<View>(R.id.tv_toast_message) as TextView
        val toast = Toast(mContext)
        tv_toast.text = message
        toast.view = view
        toast.duration = Toast.LENGTH_LONG
        toast.show()
    }

}
package com.app.phew.utils.views


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.phew.R
import com.app.phew.listners.OnItemClickListener
import com.app.phew.models.ListModel
import java.util.*


class ListDialog(
    internal var mContext: Context,
    internal var onItemClickListener: OnItemClickListener,
    internal var mCarsList: ArrayList<ListModel>,
    internal var title: String
) : Dialog(mContext) {

    internal var rvRecycle: RecyclerView? = null

    internal var lay_no_data: LinearLayout? = null

    internal var tv_title: TextView? = null

    lateinit var iv_close: ImageView

    lateinit var mLinearLayoutManager: LinearLayoutManager

    lateinit var mListAdapter: ListDialogsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_custom_layout)
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        rvRecycle = findViewById(R.id.rv_recycle)
        lay_no_data = findViewById(R.id.lay_no_data)
        tv_title = findViewById(R.id.tv_title)
        iv_close = findViewById(R.id.iv_close)
        window!!.setGravity(Gravity.CENTER)
        setCancelable(false)
//        ButterKnife.bind(this)
        initializeComponents()
    }

    private fun initializeComponents() {
        tv_title!!.text = title
        mLinearLayoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        rvRecycle!!.layoutManager = mLinearLayoutManager
        mListAdapter = ListDialogsAdapter(mContext, mCarsList)
        mListAdapter.setOnItemClickListener(onItemClickListener)
        rvRecycle!!.adapter = mListAdapter

        if (mCarsList.size == 0) {
            lay_no_data!!.visibility = View.VISIBLE
        }

        iv_close.setOnClickListener(View.OnClickListener {
            dismiss()
        })
    }


}

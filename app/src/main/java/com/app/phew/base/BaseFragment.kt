package com.app.phew.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.phew.preferences.LanguagePrefManager
import com.app.phew.preferences.SharedPrefManager
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.DialogUtil


abstract class BaseFragment : Fragment() {

    lateinit var mSharedPrefManager: SharedPrefManager

    protected var mLanguagePrefManager: LanguagePrefManager? = null

    protected lateinit var mContext: Context

    var mSavedInstanceState: Bundle? = null

    private var mProgressDialog: ProgressDialog? = null

    protected var shouldExcuteOnResume: Boolean = false

    protected abstract val layoutResource: Int

    /**
     * if the current activity is a recycle
     */
    protected abstract val isRecycle: Boolean


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResource, container, false)
        mContext = activity!!
        mSharedPrefManager = SharedPrefManager(mContext)
        mLanguagePrefManager =
            LanguagePrefManager(mContext as FragmentActivity)
        shouldExcuteOnResume = false
        this.mSavedInstanceState = savedInstanceState

//        if (isRecycle) {
//            ConfigRecycle(view)
//        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (isRecycle) {
            ConfigRecycle(view)
        }

        initializeComponents(view)
    }

    protected abstract fun initializeComponents(view: View)

    protected fun onRealResume() {}


    override fun onResume() {
        super.onResume()
        if (shouldExcuteOnResume) {
            onRealResume()
        } else {
            shouldExcuteOnResume = true
        }
    }

    protected fun showProgressDialog(message: String) {
        mProgressDialog = DialogUtil.showProgressDialog(this.activity!!, message, false)
    }

    protected fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    protected fun ConfigRecycle(view: View) {
       // CommonUtil.PrintLogE("Initialize Views")
//        swipeRefresh = view.findViewById<View>(R.id.swipe_refresh) as SwipeRefreshLayout
//        progressLoad = view.findViewById(R.id.progress_wheel_load)
//        layProgress = view.findViewById<View>(R.id.lay_progress) as RelativeLayout
//        progressWheel = view.findViewById<View>(R.id.progress_wheel) as ProgressWheel
//        layNoInternet = view.findViewById<View>(R.id.lay_no_internet) as RelativeLayout
//        ivNoInternet = view.findViewById<View>(R.id.iv_no_internet) as ImageView
//        layNoItem = view.findViewById<View>(R.id.lay_no_item) as RelativeLayout
//        ivNoItem = view.findViewById<View>(R.id.iv_no_item) as ImageView
//        tvNoContent = view.findViewById<View>(R.id.tv_no_content) as TextView
//        rvRecycle = view.findViewById<View>(R.id.rv_recycle) as RecyclerView
        //        layLoad = view.findViewById(R.id.lay_load);
    }


    protected fun runLayoutAnimation(recyclerView: RecyclerView, animation: Int) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, animation)

        recyclerView.clearAnimation()
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }


}

package com.app.phew.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.app.phew.utils.views.Toaster
import com.app.phew.R
import com.app.phew.preferences.LanguagePrefManager
import com.app.phew.preferences.SharedPrefManager
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.DialogUtil
import com.app.phew.utils.LocaleHelper
import java.util.*


abstract class ParentActivity : AppCompatActivity() {

    protected lateinit var mActivity: AppCompatActivity

    protected lateinit var mSharedPrefManager: SharedPrefManager

    protected lateinit var mLanguagePrefManager: LanguagePrefManager

    internal var toolbar: Toolbar? = null

    protected lateinit var mContext: Context

    private var menuId: Int = 0

    protected var mToaster: Toaster? = null

    protected var mSavedInstanceState: Bundle? = null

    private var mProgressDialog: ProgressDialog? = null


    protected var title: TextView? = null


    protected var shouldExcuteOnResume: Boolean = false

    /**
     * @return the layout resource id
     */
    protected abstract val layoutResource: Int

    /**
     * it is a boolean method which tell my if toolbar
     * is enabled or not
     */
    protected abstract val isEnableToolbar: Boolean

    /**
     * it is a boolean method which tell if full screen mode
     * is enabled or not
     */
    protected abstract val isFullScreen: Boolean

    /**
     * it is a boolean method which tell if back button
     * is enabled or not
     */
    protected abstract val isEnableBack: Boolean

    /**
     * it the current activity is a recycle
     */
    protected abstract val isRecycle: Boolean


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dm = resources.displayMetrics
        val conf = resources.configuration
        val lang = LocaleHelper.getLanguage(applicationContext)
        conf.setLocale(Locale(lang!!.toLowerCase())) // API 17+ only.
        resources.updateConfiguration(conf, dm)
        mContext = this
        mActivity = this
        mSharedPrefManager =
            SharedPrefManager(mContext as ParentActivity)
        mLanguagePrefManager =
            LanguagePrefManager(mContext as ParentActivity)
        mToaster = Toaster(mContext as ParentActivity)
        shouldExcuteOnResume = false


        if (isFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        if (hideInputType()) {
            hideInputTyping()
        }
        // set layout resources
        setContentView(layoutResource)
        this.mSavedInstanceState = savedInstanceState

        if (isEnableToolbar) {
            configureToolbar()
        }
        if (isRecycle) {
            ConfigRecycle()
        }
        initializeComponents()

    }


    fun setToolbarTitle(titleId: String) {
        if (toolbar != null) {
            title!!.text = titleId
        }
    }

    protected abstract fun initializeComponents()


    /**
     * this method is responsible for configure toolbar
     * it is called when I enable toolbar in my activity
     */
    private fun configureToolbar() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar?
        title = findViewById(R.id.tv_title)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.setTitleTextColor(Color.WHITE)  //ContextCompat.getColor(this.mContext, R.color.colorWhite))
        // check if enable back
        if (isEnableBack) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
          toolbar!!.setNavigationIcon(R.drawable.ic_back)
        }
    }

    private fun ConfigRecycle() {
//        swipeRefresh = findViewById<View>(R.id.swipe_refresh) as SwipeRefreshLayout?
//        layProgress = findViewById<View>(R.id.lay_progress) as RelativeLayout?
//        progressWheel = findViewById<View>(R.id.progress_wheel) as ProgressWheel?
//        layNoInternet = findViewById<View>(R.id.lay_no_internet) as RelativeLayout?
//        ivNoInternet = findViewById<View>(R.id.iv_no_internet) as ImageView?
//        layNoItem = findViewById<View>(R.id.lay_no_item) as RelativeLayout?
//        ivNoItem = findViewById<View>(R.id.iv_no_item) as ImageView?
//        tvNoContent = findViewById<View>(R.id.tv_no_content) as TextView?
//        rvRecycle = findViewById<View>(R.id.rv_recycle) as RecyclerView?
//        //        layLoad = findViewById(R.id.lay_load);
//        progressLoad = findViewById(R.id.progress_wheel_load)
    }

    /**
     * it is a boolean method which tell if input is
     * is appeared  or not
     */
    protected abstract fun hideInputType(): Boolean

    /**
     * this method allowed me to create option menu
     */


    fun onRealResume() {

    }

    fun createOptionsMenu(menuId: Int) {
        Log.e("test", "test")
        this.menuId = menuId
        invalidateOptionsMenu()
    }

    /**
     * this method allowed me to remove option menu
     */
    fun removeOptionsMenu() {
        menuId = 0
        invalidateOptionsMenu()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        if (menuId != 0) {
            menuInflater.inflate(menuId, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
    override fun onResume() {
        super.onResume()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (shouldExcuteOnResume) {
            onRealResume()
        } else {
            shouldExcuteOnResume = true
        }
    }

    fun hideInputTyping() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    protected fun showProgressDialog(message: String) {
        mProgressDialog = DialogUtil.showProgressDialog(this, message, false)
    }

    protected fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    protected fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
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




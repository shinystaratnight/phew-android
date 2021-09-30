package com.app.phew.ui.mostInterActiveCitiesPosts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.ui.home.HomeFragment
import com.app.phew.ui.main.MainActivity

class MostInterActiveCitiesPostsActivity :  ParentActivity() {
    override val layoutResource: Int
        get() = R.layout.activity_most_inter_active_cities_posts
    override val isEnableToolbar: Boolean
        get() = true
    override val isFullScreen: Boolean
        get() = false
    override val isEnableBack: Boolean
        get() = true
    override val isRecycle: Boolean
        get() = false



    override fun hideInputType() = true

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity,url:String) {
            appCompatActivity.
            startActivity(
                Intent(appCompatActivity, MostInterActiveCitiesPostsActivity::class.java).
                putExtra("url",url))
        }
    }

    private  var url = ""
    override fun initializeComponents() {
        url = intent?.extras?.getString("url")?:""
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setToolbarTitle(getString(R.string.the_most_interactive_cities))
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flMainClient, HomeFragment("most",url))
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
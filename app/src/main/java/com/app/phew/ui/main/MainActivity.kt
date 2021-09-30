package com.app.phew.ui.main

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.listners.IOnBackPressed
import com.app.phew.ui.chat.ChatFragment
import com.app.phew.ui.dashboard.FindlyFragment
import com.app.phew.ui.home.HomeFragment
import com.app.phew.ui.notifications.NotificationsFragment
import com.app.phew.ui.search.SearchActivity
import com.app.phew.ui.settings.SettingsActivity
import com.app.phew.ui.showProfile.ShowProfileActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karan.churi.PermissionManager.PermissionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ParentActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override val layoutResource: Int
        get() = R.layout.activity_main
    override val isEnableToolbar: Boolean
        get() = false
    override val isFullScreen: Boolean
        get() = false
    override val isEnableBack: Boolean
        get() = false
    override val isRecycle: Boolean
        get() = false

    override fun hideInputType() = true

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity) {
            appCompatActivity.startActivity(Intent(appCompatActivity, MainActivity::class.java))
        }
    }

    private lateinit var homeFragment: HomeFragment
    private lateinit var findlyFragment: FindlyFragment
    private lateinit var notificationsFragment: NotificationsFragment
    private lateinit var chatFragment: ChatFragment
    private lateinit var currentFragment: Fragment

    override fun initializeComponents() {
        bnvMainClient.setOnNavigationItemSelectedListener(this)
        homeFragment = HomeFragment("main","posts")
        findlyFragment = FindlyFragment()
        notificationsFragment = NotificationsFragment()
        chatFragment = ChatFragment()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        currentFragment = homeFragment
        replaceFragment(currentFragment)
        val  permission = object : PermissionManager() {

        }
        permission.checkAndRequestPermissions(this)
        Glide.with(this).load(mSharedPrefManager.userData.profile_image).into(imvProfileImage)
        imvProfileImage.setOnClickListener {
            ShowProfileActivity.startActivity(this,mSharedPrefManager.userData.id!!)
        }
        bnvMainClient.setItemIconTintList(null);

        imvSettings.setOnClickListener {
            SettingsActivity.startActivity(this)
        }
        imvSearch.setOnClickListener {
            SearchActivity.startActivity(this)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.navigation_home -> if (currentFragment != homeFragment) {
            currentFragment = homeFragment
            replaceFragment(currentFragment)
            true
        } else false
        R.id.navigation_dashboard -> if (currentFragment != findlyFragment) {
            currentFragment = findlyFragment
            replaceFragment(currentFragment)
            true
        } else false
        R.id.navigation_notifications -> if (currentFragment != notificationsFragment) {
            currentFragment = notificationsFragment
            replaceFragment(currentFragment)
            true
        } else false
        R.id.navigation_chat -> if (currentFragment != chatFragment) {
            currentFragment = chatFragment
            replaceFragment(currentFragment)
            true
        } else false
        else -> false
    }

    private fun replaceFragment(destinationFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flMainClient, destinationFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.flMainClient)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()
    }
}
package com.app.phew.ui.signing

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentActivity
import com.app.phew.ui.signing.login.LoginFragment
import com.app.phew.ui.signing.register.RegisterFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_signing.*

class SignIngActivity : ParentActivity() {
    override val layoutResource: Int
        get() = R.layout.activity_signing
    override val isEnableToolbar: Boolean
        get() = false
    override val isFullScreen: Boolean
        get() = false
    override val isEnableBack: Boolean
        get() = false
    override val isRecycle: Boolean
        get() = false

    override fun hideInputType() = false

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity) {
            appCompatActivity.startActivity(Intent(appCompatActivity, SignIngActivity::class.java))
        }

        const val RC_SIGN_IN = 1992
    }

    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun initializeComponents() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.barColor)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(LoginFragment(), resources.getString(R.string.login))
        adapter.addFragment(RegisterFragment(), resources.getString(R.string.register))
        sinIngViewPager.adapter = adapter
        signIngTab.setupWithViewPager(sinIngViewPager)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id)).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun goLogin() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.w("API123", "signInResult:RequestCode= $requestCode")
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.w("API123", "signInResult:Account: ${account.email}")
            getGOProfile(account)
        } catch (e: ApiException) {
            Log.w("API123", "signInResult:failed:" + e.message)
            Log.w("API123", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun getGOProfile(account: GoogleSignInAccount) {
        val fullName = account.displayName?.toString()
        val token = account.idToken?.toString()
//        val email = account.email?.toString()
//        val id = account.id?.toString()
//        val imageUrl = account.photoUrl?.toString()
        if (!mSharedPrefManager.deviceToken.isNullOrEmpty()) LoginFragment().mPresenter.socialLogin(
                fullName.toString(), "google", token.toString(),
                "android", mSharedPrefManager.deviceToken
        )
    }
}
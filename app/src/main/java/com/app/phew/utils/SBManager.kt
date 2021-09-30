package com.app.phew.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.app.phew.R

object SBManager {

    private var snack: Snackbar? = null

    private fun initSnack(context: Context): Snackbar {
        snack = Snackbar.make(
            (context as Activity).findViewById(android.R.id.content),
            "",
            Snackbar.LENGTH_LONG
        )
        snack?.setAction("(x)") { snack?.dismiss() }
        snack?.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        snack?.view?.layoutParams =
            (snack?.view?.layoutParams as FrameLayout.LayoutParams).apply { gravity = Gravity.TOP }

        return snack!!
    }

    fun displayError(context: Context, errMsg: String? = null) {
        snack = initSnack(context)
        snack?.setBackgroundTint(Color.RED)
        snack?.setActionTextColor(Color.WHITE)
        snack?.setTextColor(Color.WHITE)
        snack?.setText(errMsg ?: context.getString(R.string.error))
        snack?.show()
        Log.d("check_snack", "err snake")
    }
    fun displayError(context: Context, errMsg: Int? = null) {
        snack = initSnack(context)
        snack?.setBackgroundTint(Color.RED)
        snack?.setActionTextColor(Color.WHITE)
        snack?.setTextColor(Color.WHITE)
        snack?.setText(context.getString(errMsg ?: R.string.error))
        snack?.show()
        Log.d("check_snack", "err snake")
    }

    fun displayMessage(context: Context, msg: String? = null) {
        snack = initSnack(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            snack?.setBackgroundTint(
                context.resources.getColor(R.color.colorPrimary, context.theme)
            )
            snack?.setActionTextColor(Color.WHITE)
            snack?.setTextColor(Color.WHITE)
        } else {
            snack?.setBackgroundTint(Color.WHITE)
            snack?.setActionTextColor(Color.BLACK)
            snack?.setTextColor(Color.BLACK)
        }
        snack?.setText(msg ?: context.getString(R.string.done))
        snack?.show()
        Log.d("check_snack", "scs snake")
    }
}
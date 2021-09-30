package com.app.phew.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils


/**
 * @author Duaa Ali
 * @LastModified 23/11/2015
 */

class Animator/*
     * Constructor
     */
(private val context: Context) {


    /*
     * Load Animation File according to animationResourceID then Start Animation
     */
    fun loadAnimation(view: View, animResource: Int): Animation {


        val anim = AnimationUtils.loadAnimation(context, animResource)
        anim.reset()
        view.clearAnimation()
        view.startAnimation(anim)
        return anim
    }


}

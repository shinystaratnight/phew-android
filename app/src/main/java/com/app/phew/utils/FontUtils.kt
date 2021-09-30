package com.app.phew.utils

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.tabs.TabLayout


object FontUtils {

    fun TabCustomFontSize(context: Context, tabLayout: TabLayout, Font: String) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildCount = vgTab.childCount
            for (i in 0 until tabChildCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    FonTChange(
                        context,
                        tabViewChild,
                        Font
                    )
                }
            }
        }
    }

    private fun FonTChange(con: Context, textView: TextView, Fonts: String) {
        val fontPath = "fonts/$Fonts"
        // Loading Font Face
        val tf = Typeface.createFromAsset(con.assets, fontPath)
        // Applying font
        textView.typeface = tf
    }
}

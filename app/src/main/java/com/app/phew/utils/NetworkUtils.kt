package com.app.phew.utils

import android.content.Context
import android.net.ConnectivityManager


object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
    //    class DoneOnEditorActionListener implements OnEditorActionListener {
    //        @Override
    //        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    //            if (actionId == EditorInfo.IME_ACTION_DONE) {
    //                KeyboardUtils.hideSoftInput((Activity) AppController.getInstance());
    //                return true;
    //            }
    //            return false;
    //        }
    //    }
}// This utility class is not publicly instantiable

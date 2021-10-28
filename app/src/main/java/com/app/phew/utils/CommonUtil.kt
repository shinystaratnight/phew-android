package com.app.phew.utils

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.*
import android.location.Geocoder
import android.media.ExifInterface
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.text.format.DateUtils
import android.text.format.DateUtils.MINUTE_IN_MILLIS
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.gson.Gson
import com.app.phew.BuildConfig
import com.app.phew.R
import com.app.phew.app.AppController
import com.app.phew.fcm.MyFirebaseInstanceIDService
import com.app.phew.preferences.SharedPrefManager
import com.app.phew.utils.views.Toaster
import com.google.android.gms.common.GoogleApiAvailability
import java.io.IOException
import java.net.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CommonUtil {

    fun showParElevation(showHide: Boolean, app_bar: AppBarLayout, elevation: Float) {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            if (showHide) {
                app_bar.elevation = elevation

            } else {
                app_bar.elevation = 0.0.toFloat()
            }
        }
    }

    companion object {

        var isALog = true

        fun onPrintLog(o: Any) {
            if (isALog) {
                Log.e("Response >>>>", Gson().toJson(o))
            }
        }

        fun PrintLogE(print: String) {
            if (BuildConfig.DEBUG) {
                Log.e(AppController.TAG, print)
            }
            Log.e(AppController.TAG, print)
        }

        fun getDeviceToken(context: Context, sharedPrefManager: SharedPrefManager) {
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
                    == com.google.android.gms.common.ConnectionResult.SUCCESS
            ) {
                FirebaseApp.initializeApp(context)
                MyFirebaseInstanceIDService.getToken(context)
                sharedPrefManager.deviceToken = MyFirebaseInstanceIDService.deviceToken
                Log.i("Email", "get token , ${sharedPrefManager.deviceToken}")
            } else {
                /*object : Thread() {
                    override fun run() {
                        try {
                            // read from agconnect-services.json
                            val appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id")
                            sharedPrefManager.deviceToken = HmsInstanceId.getInstance(context).getToken(appId, "HCM")
                            Log.i("Elasil", "get token , ${sharedPrefManager.deviceToken}")
                        } catch (e: ApiException) {
                            Log.e("Elasil", "get token failed, $e")
                        }
                    }
                }.start()*/
            }
        }
        fun makeURL(
            sourceLat: Double,
            sourceLog: Double,
            destLat: Double,
            destLog: Double
        ): String {
            val urlString = StringBuilder()
            urlString.append("https://maps.googleapis.com/maps/api/directions/json")
            urlString.append("?origin=")// from
            urlString.append(java.lang.Double.toString(sourceLat))
            urlString.append(",")
            urlString.append(java.lang.Double.toString(sourceLog))
            urlString.append("&destination=")// to
            urlString.append(java.lang.Double.toString(destLat))
            urlString.append(",")
            urlString.append(java.lang.Double.toString(destLog))
            urlString.append("&sensor=false&mode=DRIVING&alternatives=true")
            urlString.append(",")
            urlString.append("&key=AIzaSyArHOxwlpYCDQRkmyRxuO_L4rMr4KMPxbs")


            return urlString.toString()
        }

        fun callDriver(mobile: String, context: Context) {
            context.startActivity(
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:${mobile}"))
            )
        }

        val language: String
            get() = Locale.getDefault().displayLanguage

        fun requestFocus(view: View, window: Window) {
            if (view.requestFocus()) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        }

        fun ShareApp(context: Context) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out Halaky at: https://play.google.com/store/apps/details?id=com.halaky"
            )
            sendIntent.type = "text/plain"
            sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(sendIntent)
        }

        fun RateApp(context: AppCompatActivity) {
            val appPackageName =
                context.packageName // getPackageName() from Context or Activity object
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }

        }

        fun handleException(context: Context, t: Throwable): Int {
            when (t) {
                is SocketTimeoutException -> {
                    makeToast(
                        context,
                        R.string.time_out_error
                    )
                    return R.string.time_out_error
                }
                is UnknownHostException -> {
                    makeToast(
                        context,
                        R.string.connection_error
                    )
                    return R.string.connection_error
                }
                is ConnectException -> {
                    makeToast(
                        context,
                        R.string.connection_error
                    )
                    return R.string.connection_error
                }
                is NoRouteToHostException -> {
                    makeToast(
                        context,
                        R.string.connection_error
                    )
                    return R.string.connection_error
                }
                is PortUnreachableException -> {
                    makeToast(
                        context,
                        R.string.connection_error
                    )
                    return R.string.connection_error
                }
                is UnknownServiceException -> {
                    makeToast(
                        context,
                        R.string.connection_error
                    )
                    return R.string.connection_error
                }
                is BindException -> {
                    makeToast(
                        context,
                        R.string.connection_error
                    )
                    return R.string.connection_error
                }
                else -> {
                    makeToast(
                        context,
                        R.string.connection_error
                    )
                    return R.string.connection_error
                }
            }
        }

        fun makeToast(context: Context, msgId: Int) {
            val toaster = Toaster(context)
            toaster.makeToast(context.getString(msgId))

        }

        fun makeToast(context: Context, msg: String) {
            val toaster = Toaster(context)
            toaster.makeToast(msg)

        }

        fun setConfig(language: String, context: Context) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            context.resources.updateConfiguration(
                config,
                context.resources.displayMetrics
            )

        }

        @Throws(IOException::class)
        fun onGetBitmap(photoPath: String, bitmap: Bitmap): Bitmap? {
            val ei = ExifInterface(photoPath)
            val orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            var rotatedBitmap: Bitmap? = null
            when (orientation) {

                ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap =
                    rotateImage(bitmap, 90f)

                ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap =
                    rotateImage(
                        bitmap,
                        180f
                    )

                ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap =
                    rotateImage(
                        bitmap,
                        270f
                    )

                ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
                else -> rotatedBitmap = bitmap
            }
            return rotatedBitmap
        }

        fun rotateImage(source: Bitmap, angle: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(angle)
            return Bitmap.createBitmap(
                source, 0, 0, source.width, source.height,
                matrix, true
            )
        }

        fun getFormattedTime(date: String): String {
            var parse: Date? = null
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                parse = sdf.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            calendar.time = parse
            val updated = calendar.timeInMillis
            return DateUtils.getRelativeTimeSpanString(
                updated,
                System.currentTimeMillis(),
                MINUTE_IN_MILLIS
            ).toString()
        }


        // Glide url
//        fun loadImage(context: Context, posterPath: String): DrawableRequestBuilder<String> {
//            return Glide
//                .with(context)
//                .load(posterPath)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//        }

        fun openWhatsappContact(context: AppCompatActivity, number: String) {
            val uri = Uri.parse("smsto:$number")
            val mWhatsAppIntent = Intent(Intent.ACTION_SENDTO, uri)
            mWhatsAppIntent.setPackage("com.whatsapp")
            context.startActivity(Intent.createChooser(mWhatsAppIntent, ""))
        }


//        fun getStakenumbers(context: Context): Int {
//            val m = context
//                    .getSystemService(context.ACTIVITY_SERVICE) as ActivityManager
//            val runningTaskInfoList = m.getRunningTasks(10)
//            val itr = runningTaskInfoList.iterator()
//            var numOfActivities = 0
//            while (itr.hasNext()) {
//                val runningTaskInfo = itr.next() as RunningTaskInfo
//                val id = runningTaskInfo.id
//                val desc = runningTaskInfo.description
//                numOfActivities = runningTaskInfo.numActivities
//                val topActivity = runningTaskInfo.topActivity
//                        .shortClassName
//                CommonUtil.PrintLogE("Activities number : $numOfActivities Top Activies : $topActivity")
//                return numOfActivities
//            }
//            return numOfActivities
//        }


        private fun FonTChange(con: Context, textView: TextView, Fonts: String) {
            val fontPath = "font/$Fonts"
            // Loading Font Face
            val tf = Typeface.createFromAsset(con.assets, fontPath)
            // Applying font
            textView.typeface = tf
        }


        var snackbar: Snackbar? = null
        fun showSnackBar(
            mContext: Context,
            coordinatorLayout: View,
            message: String,
            ColorRes: Int,
            bgColor: Int
        ) {

            snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT)
            snackbar?.setActionTextColor(Color.RED)
            val sbView = snackbar?.view

            val view = snackbar?.view
            val params = view?.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            view.layoutParams = params
            snackbar?.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
            sbView?.setBackgroundColor(mContext.resources.getColor(bgColor))
            val textView = sbView?.findViewById(R.id.snackbar_text) as TextView
            // CommonUtil.FonTChange(mContext, textView, Fonts.regularFont)
            textView.setTextColor(ContextCompat.getColor(mContext, ColorRes))
            snackbar?.show()
        }

        fun showSnackBarWithAction(
            mContext: Context,
            coordinatorLayout: View,
            message: String,
            ColorRes: Int,
            bgColor: Int,
            actionText: String,
            lisnter: View.OnClickListener
        ) {
//            val snackbar = Snackbar
//                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
//            snackbar.setAction(actionText , lisnter)
//            val sbView = snackbar.view
//            sbView.setBackgroundColor(mContext.resources.getColor(bgColor))
//            val textView = sbView.findViewById(R.id.snackbar_text) as TextView
//            val actionTextView = sbView.findViewById(R.id.snackbar_action) as TextView
//            CommonUtil.FonTChange(mContext, textView, Fonts.regularFont)
//            CommonUtil.FonTChange(mContext, actionTextView, Fonts.regularFont)
//            textView.setTextColor(ContextCompat.getColor(mContext, ColorRes))
//            actionTextView.setTextColor(ContextCompat.getColor(mContext, ColorRes))
//            snackbar.show()
            val snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_SHORT)
            snackbar.setActionTextColor(Color.RED)
            val sbView = snackbar.view
            sbView.setBackgroundColor(mContext.resources.getColor(bgColor))
            val textView = sbView.findViewById(R.id.snackbar_text) as TextView
            FonTChange(
                mContext,
                textView,
                Fonts.regularFont
            )
            textView.setTextColor(ContextCompat.getColor(mContext, ColorRes))
            snackbar.show()
        }

        fun showSnackBarWithLong(
            mContext: Context,
            coordinatorLayout: View,
            message: String,
            ColorRes: Int
        ) {
            val snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.RED)
            val sbView = snackbar.view
            val textView = sbView.findViewById(R.id.snackbar_text) as TextView
            FonTChange(
                mContext,
                textView,
                Fonts.regularFont
            )
            textView.setTextColor(ContextCompat.getColor(mContext, ColorRes))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            snackbar.show()
        }


        fun setStrokInText(textView: TextView) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        fun setStrokBelowText(textView: TextView) {
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }

        fun getCompleteAddressString(
            mContext: Context,
            LATITUDE: Double,
            LONGITUDE: Double
        ): String {
            var strAdd = ""
            val geocode = Geocoder(mContext, Locale.getDefault())
            try {
                val addresses = geocode.getFromLocation(LATITUDE, LONGITUDE, 1)
                if (addresses != null) {
                    val returnedAddress = addresses[0]
                    val strReturnedAddress = StringBuilder()

                    for (i in 0..returnedAddress.maxAddressLineIndex) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                    }
                    strAdd = strReturnedAddress.toString()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return strAdd
        }

        fun getDeviceToken(context: Context): String {
            FirebaseApp.initializeApp(context)
            MyFirebaseInstanceIDService.getToken(context)
            return MyFirebaseInstanceIDService.deviceToken
        }
    }
}
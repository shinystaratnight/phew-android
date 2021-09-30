package com.app.phew.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import com.app.phew.R
import com.shashank.sony.fancydialoglib.Animation
import com.shashank.sony.fancydialoglib.FancyAlertDialog
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener
import com.shashank.sony.fancydialoglib.Icon



object DialogUtil {

    fun showProgressDialog(context: Context, message: String, cancelable: Boolean): ProgressDialog {
        val dialog = ProgressDialog(context)
        dialog.setMessage(message)
        dialog.setCancelable(cancelable)
        dialog.show()
        return dialog
    }


    fun showAlertDialog(
        context: Context, message: String,
        negativeClickListener: DialogInterface.OnClickListener?
    ): AlertDialog {
        // create the dialog builder & set message
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(message)
        // check negative click listener
        if (negativeClickListener != null) {
            // not null
            // add negative click listener
            dialogBuilder.setNegativeButton(context.getString(R.string.yes), negativeClickListener)


        } else {
            // null
            // add new click listener to dismiss the dialog
            dialogBuilder.setNegativeButton(context.getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
        }
        // create and show the dialog

        val dialog = dialogBuilder.create()
        dialog.show()
        return dialog
    }


    fun showAlertDialognotCancelable(
        context: Context, message: String,
        negativeClickListener: DialogInterface.OnClickListener?
    ): AlertDialog {
        // create the dialog builder & set message
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(message)
        // check negative click listener
        if (negativeClickListener != null) {
            // not null
            // add negative click listener
            dialogBuilder.setNegativeButton(context.getString(R.string.ok), negativeClickListener)
        } else {
            // null
            // add new click listener to dismiss the dialog
            dialogBuilder.setNegativeButton(context.getString(R.string.no)) { dialog, which -> dialog.dismiss() }
        }
        // create and show the dialog

        val dialog = dialogBuilder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }


    fun showconfirm(
        context: Context, tittle: Int, message: String, cancble: Boolean,
        negativeClickListener: DialogInterface.OnClickListener?, positiveClickListener: DialogInterface.OnClickListener?
    ): AlertDialog {
        // create the dialog builder & set message
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(tittle)

        dialogBuilder.setMessage(message)

        // check negative click listener
        if (negativeClickListener != null) {
            // not null
            // add negative click listener
            dialogBuilder.setNegativeButton(context.getString(R.string.cancel), negativeClickListener)
        }
        if (positiveClickListener != null) {
            // not null
            // add negative click listener
            dialogBuilder.setPositiveButton(context.getString(R.string.dialog_continue), positiveClickListener)
        }
        // create and show the dialog
        dialogBuilder.setCancelable(cancble)
        val dialog = dialogBuilder.create()
        dialog.show()

        return dialog
    }


    fun showFancyDialog(
        mActivity: Activity,
        title: String,
        message: String,
        negBtnText: String,
        posBtnText: String,
        icon: Int,
        posListner: FancyAlertDialogListener,
        negListner: FancyAlertDialogListener
    ): FancyAlertDialog {


        return FancyAlertDialog.Builder(mActivity)
            .setTitle(title)
            .setBackgroundColor(Color.parseColor("#47AFB2"))  //Don't pass R.color.colorvalue
            .setMessage(message)
            .setNegativeBtnText(negBtnText)
            .setNegativeBtnBackground(Color.parseColor("#5ab783"))  //Don't pass R.color.colorvalue
            .setPositiveBtnText(posBtnText)
            .setPositiveBtnBackground(Color.parseColor("#47AFB2"))  //Don't pass R.color.colorvalue
            .setAnimation(Animation.POP)
            .isCancellable(true)
            .setIcon(icon, Icon.Visible)
            .OnPositiveClicked(posListner)
            .OnNegativeClicked(negListner)
            .build()
    }

}

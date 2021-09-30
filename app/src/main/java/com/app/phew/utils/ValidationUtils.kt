package com.app.phew.utils

import android.content.Context
import android.text.TextUtils
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern


object ValidationUtils {

    private fun isValid(input: String): Boolean {
        var valid = true
        if (input.trim { it <= ' ' }.isEmpty()) {
            valid = false
        }
        return valid
    }

    fun emptyValidation(editText: EditText, error: String): Boolean {
        editText.error = null
        if (editText.text.toString().trim { it <= ' ' }.isEmpty()) {
            editText.error = error
            return false
        }
        return true
    }

    fun checkError(editText: EditText, textInputLayout: TextInputLayout, error_message: String): Boolean {
        return if (!isValid(editText.text.toString())) {
            textInputLayout.error = error_message
            false
        } else {
            textInputLayout.error = null
            true
        }
    }

    fun checkPassSize(editText: EditText, message: String, size: Int): Boolean {
        if (editText.text.toString().length < size) {
            editText.error = message
            return false
        } else {
            editText.error = null
            return true
        }

    }

    fun emptyValidationReturnBoolean(editText: EditText): Boolean {
        return !editText.text.toString().trim { it <= ' ' }.isEmpty()
    }


    fun passwordSizeValidation(editText: EditText): Boolean {
        if (editText.text.toString().length <= 5) {
            editText.error = "Password should be more 6 characters"
            return false
        }
        return true
    }

    fun checkNamePref(editText: EditText): Boolean {
        if (editText.text.toString().matches("^[a-zA-Z].*".toRegex())) {
            return true
        } else {
            editText.error = "user name shouldn't startDialogUtil's with number or special character"
            return false
        }
    }


    fun validateConfirmPassword(et_user_password: EditText, et_confirm_password: EditText): Boolean {
        if (et_confirm_password.text.toString() != et_user_password.text.toString().trim { it <= ' ' }) {
            et_confirm_password.error = "Password Not Matched"
            return false
        }
        return true
    }

    fun validateEmail(editText: EditText, textInputLayout: TextInputLayout): Boolean {
        if (editText.text.toString().trim { it <= ' ' }.isEmpty()) {
            textInputLayout.error = "Enter your Email"
            return false
        } else if (!isValidEmail(editText.text.toString().trim { it <= ' ' })) {
            textInputLayout.error = "Email is not in correct format"
            return false
        }
        return true
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkSize(editText: EditText, textInputLayout: TextInputLayout, message: String, size: Int): Boolean {
        return if (editText.text.toString().trim { it <= ' ' }.length < size) {
            textInputLayout.error = message
            false
        } else {
            textInputLayout.error = null
            true
        }

    }

    fun isEmail(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern =
            Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isEmailValid(email: EditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val emailString = email.text.toString()
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(emailString)
        if (matcher.matches() == true) {
            textInputLayout.error = null
            return true
        } else {
            textInputLayout.error = message
            return false
        }
    }

    fun checkMatch(
        pass: EditText, confirm_pass: EditText,
        message: String
    ): Boolean {
        var isMatch = false

        if (pass.text.toString().matches(confirm_pass.text.toString().toRegex())) {
            isMatch = true
        } else {
            confirm_pass.error = message
        }
        return isMatch
    }

    fun emptyValidation(context: Context, editText: TextInputEditText, errorMessage: String): Boolean {
        if (editText.text!!.toString().trim { it <= ' ' }.isEmpty()) {
            CommonUtil.makeToast(context, errorMessage)
            return false
        }
        return true
    }
}

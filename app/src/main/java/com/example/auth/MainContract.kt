package com.example.auth

import android.app.Activity
import android.content.Context
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.example.auth.model.api.UserInfo
import com.google.android.material.textfield.TextInputLayout

interface MainContract {
    interface View {
        fun showMessage(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun showOrHidePassword(passwordEditText: EditText) {
            if (passwordEditText.transformationMethod == PasswordTransformationMethod.getInstance())
                passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            else passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        fun moveToNextActivity(startActivity: Activity, moveToActivity: Activity)

        fun showError(message: String, firstField: Boolean = false)

        fun disableLoading()
    }

    interface Presenter {
        fun onLoginButtonWasClicked(userInfo: UserInfo, context: Context)

        fun onRegisterButtonWasClicked(userInfo: UserInfo, context: Context)

        fun onValidateEmailButtonWasClicked(verification_code: String, context: Context)

        fun validateEmail(email: String, textInputLayout: TextInputLayout): Boolean

        fun validatePassword(password: String, textInputLayout: TextInputLayout): Boolean

        fun validatePassword(password: String, doublePassword: String, passwordInputLayout: TextInputLayout, doublePasswordInputLayout: TextInputLayout): Boolean

        fun validateCode(code: String, textInputLayout: TextInputLayout): Boolean

    }

    interface Model {
        fun loginUser(userInfo: UserInfo, context: Context)

        fun registerUser(userInfo: UserInfo, context: Context)

        fun verificateEmail(verification_code: String, context: Context)

        fun getToken(): String

        fun getErrorMessage(): String

        fun getEmailErrorField(): Boolean
    }



}
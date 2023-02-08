package com.example.auth.presenter

import android.app.Activity
import android.content.Context
import android.os.Handler
import com.example.auth.*
import com.example.auth.model.MainModel
import com.example.auth.model.api.UserInfo
import com.google.android.material.textfield.TextInputLayout

class LoginRegisterPresenter(var mView: MainContract.View) : MainContract.Presenter {

    var mModel: MainContract.Model = MainModel()


    override fun onLoginButtonWasClicked(userInfo: UserInfo, context: Context) {
        mModel.loginUser(userInfo, context)
        val handler = Handler()
        handler.postDelayed(
            { val errorMessage = mModel.getErrorMessage()
                if (errorMessage != "") mView.showError(errorMessage)
                mView.disableLoading()},
            1000
        )

    }

    override fun onRegisterButtonWasClicked(userInfo: UserInfo, context: Context) {
        mModel.registerUser(userInfo, context)
        val activity = mView as RegisterActivity
        val handler = Handler()
        handler.postDelayed(
            {val errorMessage = mModel.getErrorMessage()
                if (errorMessage != "") mView.showError(errorMessage, mModel.getEmailErrorField())
                else activity.moveToNextActivity(activity, ValidateEmailActivity())
                mView.disableLoading()},
            2000
        )

    }

    override fun onValidateEmailButtonWasClicked(verification_code: String, context: Context) {
        mModel.verificateEmail(verification_code, context)
        val activity = mView as ValidateEmailActivity
        val handler = Handler()
        handler.postDelayed(
                {val errorMessage = mModel.getErrorMessage()
                    if (errorMessage != "") mView.showMessage(context, errorMessage)
                    else {
                    activity.moveToNextActivity(activity, FullRegistrationActivity())
                        mView.disableLoading()
                }},
                1000
            )
    }

    override fun validateEmail(email: String, textInputLayout: TextInputLayout): Boolean {
        return if (email == "") {
            textInputLayout.error = "Введите email"
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayout.error = "Введите корректный email"
            false
        } else {
            textInputLayout.error = null
            true
        }
    }

    override fun validatePassword(password: String, textInputLayout: TextInputLayout): Boolean {
        return if (password == "") {
            textInputLayout.error = "Введите пароль"
            false
        } else {
            textInputLayout.error = null
            true
        }
    }

    override fun validatePassword(password: String, doublePassword: String, passwordInputLayout: TextInputLayout, doublePasswordInputLayout: TextInputLayout
    ): Boolean {
        if (password == "") {
            passwordInputLayout.error = "Введите пароль"
            return false
        }
        else if (doublePassword == "") {
            doublePasswordInputLayout.error = "Введите пароль для подтверждения"
            passwordInputLayout.error = null
            return false
        }
        else if (password != doublePassword) {
            doublePasswordInputLayout.error = "Введенные пароли не совпадают"
            passwordInputLayout.error = null
            return false
        }
        doublePasswordInputLayout.error = null
        return true
    }

    override fun validateCode(code: String, textInputLayout: TextInputLayout): Boolean {
        return if (code == "") {
            textInputLayout.error = "Введите код подтверждения"
            false
        } else {
            textInputLayout.error = null
            true
        }
    }
}
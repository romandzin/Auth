package com.example.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.auth.model.api.UserInfo
import com.example.auth.presenter.LoginRegisterPresenter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.log

class AuthorizeActivity : AppCompatActivity(), MainContract.View {

    lateinit var mPresenter: MainContract.Presenter
    lateinit var passwordInput: TextInputLayout
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorize)
        mPresenter = LoginRegisterPresenter(this)
        initView()
    }

    private fun initView() {
        progressBar = findViewById(R.id.progressBar)
        val emailEditText = findViewById<EditText>(R.id.emailField)
        val emailInput = findViewById<TextInputLayout>(R.id.emailInput)
        emailInput.errorIconDrawable = null
        val passwordEditText = findViewById<EditText>(R.id.passwordField)
        passwordInput = findViewById(R.id.passwordInput)
        passwordInput.errorIconDrawable = null
        val noAccountText = findViewById<TextView>(R.id.noAccountText)
        noAccountText.setOnClickListener {
            moveToNextActivity(this, RegisterActivity())
        }
        val loginButton = findViewById<Button>(R.id.authorizeButton)
        loginButton.setOnClickListener {
            val loginInfo = UserInfo(email = emailEditText.text.toString(), password = passwordEditText.text.toString())
            if (mPresenter.validateEmail(loginInfo.email, emailInput) && mPresenter.validatePassword(loginInfo.password, passwordInput)) {
                progressBar.visibility = View.VISIBLE
                mPresenter.onLoginButtonWasClicked(loginInfo, this)
            }
        }
        val showPasswordButton = findViewById<ImageView>(R.id.showPasswordIcon)
        showPasswordButton.setOnClickListener {
           showOrHidePassword(passwordEditText)
        }
    }

    override fun moveToNextActivity(startActivity: Activity, moveToActivity: Activity) {
        val intent = Intent(startActivity.baseContext, moveToActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity.baseContext.startActivity(intent)
    }

    override fun showError(message: String, firstField: Boolean) {
        passwordInput.error = message
    }

    override fun disableLoading() {
        progressBar.visibility = View.GONE
    }
}
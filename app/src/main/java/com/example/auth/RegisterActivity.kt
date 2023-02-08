package com.example.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.auth.model.api.UserInfo
import com.example.auth.presenter.LoginRegisterPresenter
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity(), MainContract.View {

    lateinit var mPresenter: MainContract.Presenter
    lateinit var emailInput: TextInputLayout
    lateinit var codeInput: TextInputLayout
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mPresenter = LoginRegisterPresenter(this)
        initView()
    }

    private fun initView() {
        progressBar = findViewById(R.id.progressBar)
        val emailEditText = findViewById<EditText>(R.id.emailField)
        emailInput = findViewById(R.id.emailInput)
        emailInput.errorIconDrawable = null
        val passwordEditText = findViewById<EditText>(R.id.passwordField)
        val passwordInput = findViewById<TextInputLayout>(R.id.passwordInput)
        passwordInput.errorIconDrawable = null
        val doublePasswordEditText = findViewById<EditText>(R.id.doublePasswordField)
        val doublePasswordInput = findViewById<TextInputLayout>(R.id.doublePasswordInput)
        doublePasswordInput.errorIconDrawable = null
        val code = findViewById<EditText>(R.id.codeField)
        codeInput = findViewById(R.id.codeInput)
        codeInput.errorIconDrawable = null

        val alreadyHaveAccountTextView = findViewById<TextView>(R.id.alreadyHaveAccountText)
        alreadyHaveAccountTextView.setOnClickListener {
            moveToNextActivity(this, AuthorizeActivity())
        }
        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val registerInfo = UserInfo(email = emailEditText.text.toString(), password = passwordEditText.text.toString(), password_confirmation = doublePasswordEditText.text.toString(), code = code.text.toString())
            if (mPresenter.validateEmail(registerInfo.email, emailInput) && mPresenter.validatePassword(
                    registerInfo.password, registerInfo.password_confirmation, passwordInput, doublePasswordInput
                ) && mPresenter.validateCode(registerInfo.code, codeInput)) {
                progressBar.visibility = View.VISIBLE
                mPresenter.onRegisterButtonWasClicked(registerInfo, this)
            }
        }
        val showPasswordButton = findViewById<ImageView>(R.id.showPasswordIcon)
        showPasswordButton.setOnClickListener {
            showOrHidePassword(passwordEditText)
        }
        val showDoublePasswordButton = findViewById<ImageView>(R.id.showDoublePasswordIcon)
        showDoublePasswordButton.setOnClickListener {
            showOrHidePassword(doublePasswordEditText)
        }
    }

    override fun moveToNextActivity(startActivity: Activity, moveToActivity: Activity) {
        val intent = Intent(startActivity.baseContext, moveToActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity.baseContext.startActivity(intent)
    }

    override fun showError(message: String, firstField: Boolean) {
        if (firstField) emailInput.error = message
        else codeInput.error = message
    }

    override fun disableLoading() {
        progressBar.visibility = View.GONE
    }
}
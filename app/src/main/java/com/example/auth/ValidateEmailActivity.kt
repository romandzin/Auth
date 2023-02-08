package com.example.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.example.auth.R
import com.example.auth.presenter.LoginRegisterPresenter
import com.google.android.material.textfield.TextInputLayout

class ValidateEmailActivity : AppCompatActivity(), MainContract.View {

    lateinit var mPresenter: MainContract.Presenter
    lateinit var codeInput: TextInputLayout
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate_email)
        mPresenter = LoginRegisterPresenter(this)
        initView()
    }

    private fun initView() {
        progressBar = findViewById(R.id.progressBar)
        val verificationCodeEditText = findViewById<EditText>(R.id.confirmCodeField)
        codeInput = findViewById(R.id.confirmCodeInput)
        codeInput.errorIconDrawable = null
        val confirmButton = findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            if (mPresenter.validateCode(verificationCodeEditText.text.toString(), codeInput)) {
                progressBar.visibility = View.VISIBLE
                mPresenter.onValidateEmailButtonWasClicked(verificationCodeEditText.text.toString(), this)
            }
        }
    }

    override fun moveToNextActivity(startActivity: Activity, moveToActivity: Activity) {
        val intent = Intent(startActivity.baseContext, moveToActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity.baseContext.startActivity(intent)
    }

    override fun showError(message: String, firstField: Boolean) {
            codeInput.error = message
    }

    override fun disableLoading() {
        progressBar.visibility = View.GONE
    }
}
package com.example.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView

class FullRegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_registration)
        initView()
    }

    private fun initView() {
        val genderEditText = findViewById<AutoCompleteTextView>(R.id.genderField)
        val genders = arrayOf("Мужской", "Женский")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genders)
        genderEditText.setAdapter(adapter)
        genderEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) genderEditText.showDropDown()
        }
        val personalDataText = findViewById<TextView>(R.id.personalDataText)
        personalDataText.text = Html.fromHtml("<p>Нажимая на кнопку “Регистрация” вы даете согласие на <font color=#3D88F8>обработку своих персональных данных</font></p>")
    }
}
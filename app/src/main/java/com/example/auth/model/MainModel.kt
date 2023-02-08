package com.example.auth.model

import android.accounts.AccountManager
import android.content.Context
import android.util.Log
import com.example.auth.MainContract
import com.example.auth.model.api.Common
import com.example.auth.model.api.UserInfo
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel: MainContract.Model {

    private var token: String = ""
    override fun getToken(): String {
        return token
    }

    private var errorMessage = ""
    override fun getErrorMessage(): String {
        return errorMessage
    }

    private var emailErrorField = false
    override fun getEmailErrorField(): Boolean {
        return emailErrorField
    }



    private lateinit var context: Context

    private val retrofitService = Common.retrofitService

    override fun loginUser(userInfo: UserInfo, context: Context) {
        errorMessage = ""
        this.context = context
        retrofitService.loginUser(userInfo.email, userInfo.password).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    token = response.body()?.data?.token.toString()
                    val preferences = context.getSharedPreferences("TokenPref", Context.MODE_PRIVATE)
                    preferences.edit().putString("token", token).apply()
                    Log.d("success", token)
                }
                else {
                    val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                    errorMessage = "Неправильный логин или пароль"
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                t.message?.let { Log.d("fail", it) }
            }
        }
        )
    }

    override fun registerUser(userInfo: UserInfo, context: Context) {
        this.context = context
        var emailError = ""
        var codeError = ""
        emailErrorField = false
        errorMessage = ""
        retrofitService.registerUser(userInfo.email, userInfo.password, userInfo.password_confirmation, userInfo.code).enqueue(object: Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    token = response.body()?.data?.token.toString()
                    val preferences = context.getSharedPreferences("TokenPref", Context.MODE_PRIVATE)
                    Log.d("token", token)
                    preferences.edit().putString("token", token).apply()
                }
                else {
                    val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                    if (jsonObject?.getJSONObject("data")?.has("email") == true)
                    {
                        emailError = jsonObject.getJSONObject("data").getJSONArray("email").getString(0)
                        emailErrorField = true
                    }
                    else if (jsonObject?.getJSONObject("data")?.has("code") == true)
                        codeError = jsonObject.getJSONObject("data").getJSONArray("code").getString(0)
                    errorMessage = emailError + codeError
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                t.message?.let { Log.d("error", it) }
            }
        })
    }

    override fun verificateEmail(verification_code: String, context: Context) {
        errorMessage = ""
        val preferences = context.getSharedPreferences("TokenPref", Context.MODE_PRIVATE)
        token = preferences.getString("token", "").toString()
        preferences.edit().putString("token", token).apply()
        retrofitService.verificateEmail(verification_code, "Bearer $token").enqueue(object: Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("tag", response.code().toString())
                if (!response.isSuccessful){
                    val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
                    errorMessage = jsonObject?.getString("message").toString()
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                t.message?.let { Log.d("no verification", it) }
            }

        })
    }
}
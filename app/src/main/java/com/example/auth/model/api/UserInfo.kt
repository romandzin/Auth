package com.example.auth.model.api

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String = "",
    @SerializedName("password_confirmation") val password_confirmation: String = "",
    @SerializedName("code") val code: String = "",
    @SerializedName("verification_code") val verificationCode: String = "",
    @SerializedName("success") val success: String = "",
    @SerializedName("token") val token: String = "",
    @SerializedName("data") var data: Data? = Data(),
)

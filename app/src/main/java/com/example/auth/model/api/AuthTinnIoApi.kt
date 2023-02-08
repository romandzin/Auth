package com.example.auth.model.api

import retrofit2.Call
import retrofit2.http.*

interface AuthTinnIoApi {

    @Headers("Content-Type: application/json")
    @POST("register")
    fun registerUser(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("password_confirmation") password_confirmation: String,
        @Query("code") code: String,
    ): Call<UserInfo>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String,
    ): Call<UserInfo>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("verification/email")
    fun verificateEmail(
        @Query("verification_code") verificationCode: String,
        @Header("Authorization") token: String
    ): Call<UserInfo>
}
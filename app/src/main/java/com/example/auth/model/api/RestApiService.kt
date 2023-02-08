package com.example.auth.model.api

object Common {
    val retrofitService: AuthTinnIoApi get() = Client.buildService(AuthTinnIoApi::class.java)
}
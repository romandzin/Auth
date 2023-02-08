package com.example.auth.model.api

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token") var token : String? = null
)

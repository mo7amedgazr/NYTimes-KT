package com.example.nytimes.data.common.response

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("status") val status: String? = null,
    @SerializedName("message") var message: String? = null,
)
package com.demo.demologgin.model

import com.google.gson.annotations.SerializedName

class Responsive(
    @SerializedName("data")
    val data:List<User>,
    @SerializedName("code")
    val code: String?,
    @SerializedName("message")
    val message: String?,
) {
}
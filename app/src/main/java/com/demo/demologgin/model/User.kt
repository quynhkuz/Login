package com.demo.demologgin.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("phone")
    val phone: String?
) {

}
package com.demo.demologgin.model

import com.globits.mita.data.network.AuthApi
import com.google.gson.annotations.SerializedName

data class Body(

    @SerializedName("code")
    val code: String? = null,
    @SerializedName("name")
    val name: String? = null,
) {


}
package uz.yuzka.a100kadmin.data.response

import com.google.gson.annotations.SerializedName

data class LoginDto(

    @SerializedName("data")
    val token: String,

    @SerializedName("message")
    val message: String

)


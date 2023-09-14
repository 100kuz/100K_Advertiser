package uz.yuzka.seller.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginRequest(

    @SerializedName("phone")
    val phone: String,

    @SerializedName("code")
    val code: String

) : Serializable

data class PasswordRequest(

    @SerializedName("phone")
    val phone: String,

) : Serializable

data class PasswordResponse(

    @SerializedName("message")
    val phone: String,

) : Serializable
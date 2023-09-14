package uz.yuzka.seller.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetMoneyRequest(

    @SerializedName("account")
    val account: String,

    @SerializedName("amount")
    val amount: Long

) : Serializable
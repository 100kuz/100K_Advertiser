package uz.yuzka.a100kadmin.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetMoneyRequest(

    @SerializedName("card_number")
    val account: String,

    @SerializedName("amount")
    val amount: Long

) : Serializable
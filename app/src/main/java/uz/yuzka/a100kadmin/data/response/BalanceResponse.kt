package uz.yuzka.a100kadmin.data.response

import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("data")
    val `data`: BalanceSto?
)
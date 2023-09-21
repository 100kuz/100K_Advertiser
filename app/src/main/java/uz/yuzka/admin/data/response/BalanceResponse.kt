package uz.yuzka.admin.data.response

import com.google.gson.annotations.SerializedName

data class BalanceResponse(
    @SerializedName("data")
    val `data`: BalanceSto?
)
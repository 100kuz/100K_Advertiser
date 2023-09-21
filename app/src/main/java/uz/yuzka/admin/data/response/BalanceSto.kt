package uz.yuzka.admin.data.response

import com.google.gson.annotations.SerializedName

data class BalanceSto(
    @SerializedName("all_products_sum")
    val all_products_sum: Long?,

    @SerializedName("balance")
    val balance: Long?,

    @SerializedName("cancelled_products_sum")
    val cancelled_products_sum: Long?,

    @SerializedName("hold_balance")
    val hold_balance: Long?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("withdrawn_sum")
    val withdrawn_sum: Long?
)
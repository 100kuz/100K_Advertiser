package uz.yuzka.a100kadmin.data.response

import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("balance")
    val balance: Long?,

    @SerializedName("defective_products_count")
    val defective_products_count: Long?,

    @SerializedName("defective_products_price")
    val defective_products_price: Long?,

    @SerializedName("earnings_week")
    val earnings_week: Long?,

    @SerializedName("earnings_month")
    val earnings_month: Long?,

    @SerializedName("hold_balance")
    val hold_balance: Long?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("products_in_storage_count")
    val products_in_storage_count: Int,

    @SerializedName("products_in_storage_price")
    val products_in_storage_price: Long,

    @SerializedName("profit_week")
    val profit_week: Long,

    @SerializedName("profit_month")
    val profit_month: Long,

    @SerializedName("returned_products_count")
    val returned_products_count: Long,

    @SerializedName("returned_products_price")
    val returned_products_price: Long,

    @SerializedName("withdrawal_balance")
    val withdrawal_balance: Long,

    @SerializedName("withdrawn_balance")
    val withdrawn_balance: Long,

    @SerializedName("chats_count")
    val chats_count: Int? = 0
)
package uz.yuzka.admin.data.response

import com.google.gson.annotations.SerializedName
import uz.yuzka.admin.base.Response

typealias SalesResponse = Response<List<OrderItem>>

data class SalesDto(

    @SerializedName("created_at")
    val created_at: String?,

    @SerializedName("deleted_at")
    val deleted_at: String?,

    @SerializedName("delivered_at")
    val delivered_at: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("income_price")
    val income_price: Int?,

    @SerializedName("order_id")
    val order_id: Int?,

    @SerializedName("platform_fee")
    val platform_fee: Int?,

    @SerializedName("admin_fee")
    val admin_fee: Int?,

    @SerializedName("price")
    val price: Int?,

    @SerializedName("product_id")
    val product_id: Int?,

    @SerializedName("product_title")
    val product_title: String?,

    @SerializedName("quantity")
    val quantity: Int?,

    @SerializedName("seller_id")
    val seller_id: Int?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("updated_at")
    val updated_at: String?,

    @SerializedName("withdrawn")
    val withdrawn: Int?,

    @SerializedName("to_withdraw")
    val to_withdrawn: Int?,

    @SerializedName("created_at_label")
    val createdAtLabel: String?,

    @SerializedName("status_label")
    val statusLabel: String?,

    var page: Int? = null
)


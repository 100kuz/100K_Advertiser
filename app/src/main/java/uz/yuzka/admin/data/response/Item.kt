package uz.yuzka.admin.data.response

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("quantity")
    val quantity: Int
)

data class ItemsResponse(
    @SerializedName("data")
    val data: List<ItemsDto>,

    @SerializedName("temp_stats")
    val temp_stats: String?
)

data class ItemsDto(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("quantity")
    val quantity: Int? = null,

    @SerializedName("return_count")
    val returnCount: Int? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("income_avg")
    val incomeAvg: Int? = null,

    @SerializedName("sold_quantity")
    val soldQuantity: Int? = null,

    @SerializedName("incomes_count")
    val incomesQuantity: Int? = 0,

    @SerializedName("canceled_quantity")
    val canceledQuantity: Int? = null,

    @SerializedName("new_orders_count")
    val newQuantity: Int? = null,

    @SerializedName("hold_orders_count")
    val holdQuantity: Int? = null,

    @SerializedName("org_price")
    var originalPrice: Int = 0,

    @SerializedName("outgoing_sum")
    val outgoingSum: Int? = 0,

    @SerializedName("new_sales_sum")
    val newSalesSum: Int? = 0,

    @SerializedName("sku")
    val itemSku: Long? = 0,

    )

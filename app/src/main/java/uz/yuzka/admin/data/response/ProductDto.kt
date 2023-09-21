package uz.yuzka.admin.data.response

import com.google.gson.annotations.SerializedName
import uz.yuzka.admin.base.Response

typealias ProductsResponse = Response<List<ProductDto>>

data class ProductItemDto(
    @SerializedName("data")
    val data: ProductDto
)

data class ProductDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("created_at_label")
    val createdAtLabel: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("price")
    val price: Long? = null,

    @SerializedName("is_advertable")
    val isAdvertable: Boolean? = null,

    @SerializedName("is_bonus")
    val isBonus: Boolean? = null,

    @SerializedName("admin_fee")
    val adminFee: Int? = null,

    @SerializedName("deliveryman_fee")
    val deliverymanFee: Int? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("images")
    val images: ArrayList<String> = arrayListOf(),

    @SerializedName("category_name")
    val categoryName: String? = null,

    @SerializedName("parent_category_name")
    val parentCategoryName: String? = null,

    @SerializedName("quantity")
    val quantity: Int? = null,

    @SerializedName("sold_quantity")
    val soldQuantity: Int? = null,

    @SerializedName("canceled_quantity")
    val canceledQuantity: Int? = null,

    @SerializedName("items")
    val items: ArrayList<Item>? = null,

    @SerializedName("reviews_avg")
    val rating: Float?,

    @SerializedName("video")
    val video: String?,

    @SerializedName("is_approvable")
    val isApprovable: Boolean?,

    @SerializedName("is_safe_sale")
    val isSafeSale: Boolean?,
)

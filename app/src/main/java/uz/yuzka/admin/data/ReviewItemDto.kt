package uz.yuzka.admin.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response

typealias ReviewItemResponse = Response<ReviewItemDto>

data class ReviewItemDto(
    val `data`: ReviewDto
)

data class ReviewDto(
    @SerializedName("answer")
    val answer: String?,

    @SerializedName("answer_user_id")
    val answer_user_id: Int?,

    @SerializedName("content")
    val content: String,

    @SerializedName("created_at")
    val created_at: String,

    @SerializedName("created_at_label")
    val created_at_label: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("is_spam")
    val is_spam: Boolean,

    @SerializedName("is_verified")
    val is_verified: Boolean,

    @SerializedName("product_id")
    val product_id: Int,

    @SerializedName("product_image")
    val product_image: String?,

    @SerializedName("product_name")
    val product_title: String,

    @SerializedName("rating")
    val rating: Int,

    @SerializedName("read_at")
    val read_at: String,

    @SerializedName("updated_at")
    val updated_at: String?,

    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("user_name")
    val user_name: String
)
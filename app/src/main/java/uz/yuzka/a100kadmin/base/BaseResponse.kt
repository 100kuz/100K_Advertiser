package uz.yuzka.a100kadmin.base

import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class BaseResponse<T>(

    @SerializedName("data")
    val data: T,

    @SerializedName("message")
    val message: String,

    @SerializedName("code")
    val code: Int,

    @SerializedName("meta")
    val meta: Meta,

    @SerializedName("links")
    val links: Link,

    )

data class BaseBranchResponse<T>(

    @SerializedName("data")
    val data: T,

    @SerializedName("message")
    val message: String,

    )

data class BaseErrorResponse(


    @SerializedName("message")
    val message: String

)

typealias Response<T> = Response<BaseResponse<T>>
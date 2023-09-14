package uz.yuzka.a100kadmin.data.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("message")
    val message: String?
)

data class DeleteImageRequest(
    val index: Int
)
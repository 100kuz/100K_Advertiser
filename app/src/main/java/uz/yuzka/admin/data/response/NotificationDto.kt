package uz.yuzka.admin.data.response

import com.google.gson.annotations.SerializedName
import uz.yuzka.admin.base.Response

typealias NotificationsResponse = Response<List<NotificationDto>>

data class NotificationDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("created_at")
    val created_at: String,

    @SerializedName("created_at_label")
    val created_at_label: String,

    @SerializedName("title")
    val title: String?,

    @SerializedName("body")
    val body: String?,

    @SerializedName("read_at")
    val read_at: String?
)

package uz.yuzka.a100kadmin.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @SerializedName("avatar")
    val avatar: String?,
    val balance: Int?,
    val created_at: String?,
    val created_at_label: String?,
    val detail_address: String?,
    val district_id: Int?,
    val district_name: String?,
    val email: String?,
    val id: Int,
    val name: String?,
    val phone: String?,
    val rating: Int?,
    val region_id: Int?,
    val region_name: String?,
    val roles: List<String>?,
    val surname: String?
) : Parcelable
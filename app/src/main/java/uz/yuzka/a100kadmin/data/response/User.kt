package uz.yuzka.a100kadmin.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("name")
    val name: String?,

    @SerializedName("username")
    val username: String?,

    @SerializedName("id")
    val id: Int
) : Parcelable
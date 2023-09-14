package uz.yuzka.a100kadmin.base

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Meta(

    @SerializedName("current_page")
    val currentPage: Int,

    @SerializedName("from")
    val from: Int,

    @SerializedName("last_page")
    val lastPage: Int,

    @SerializedName("links")
    val links: List<Links>,

    @SerializedName("path")
    val path: String,

    @SerializedName("per_page")
    val perPage: Int,

    @SerializedName("to")
    val to: Int,

    @SerializedName("total")
    val total: Int,

    @SerializedName("priceMin")
    val priceMin: Int?,

    @SerializedName("priceMax")
    val priceMax: Int?,

    @SerializedName("countedByRating")
    val countedByRating: CountedByRating?
)

data class Links(
    val url: String,
    val label: String,
    val active: Boolean
)

@Parcelize
data class CountedByRating(
    val rating5: Int = 0,
    val rating4: Int = 0,
    val rating3: Int = 0,
    val rating2: Int = 0,
    val rating1: Int = 0
) : Parcelable
package uz.yuzka.admin.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetMeDto(
    val `data`: Data
) : Parcelable
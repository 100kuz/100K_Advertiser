package uz.yuzka.a100kadmin.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetMeDto(
    val `data`: Data
) : Parcelable
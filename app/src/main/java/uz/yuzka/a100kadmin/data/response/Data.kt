package uz.yuzka.a100kadmin.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val address: String,
    val advertiser_group_link: String,
    val avatar: String,
    val balance: Long? = 0,
    val coins: Int,
    val created_at: String,
    val created_at_label: String,
    val district_id: Int,
    val district_name: String,
    val email: String,
    val hold_balance: Long?,
    val id: Int,
    val name: String,
    val region_id: Int,
    val region_name: String,
    val surname: String,
    val telegram_chat_id: Int,
    val username: String
) : Parcelable
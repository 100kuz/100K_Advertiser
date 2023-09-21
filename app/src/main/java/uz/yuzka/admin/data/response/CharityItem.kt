package uz.yuzka.admin.data.response

import uz.yuzka.admin.base.Response

typealias CharitiesResponse = Response<List<CharityItem>>

data class CharityItem(
    val charity: Int,
    val created_at: String,
    val discount: Int,
    val id: Int,
    val is_region_disabled: Boolean,
    val link: String,
    val name: String,
    val orders_count: Int?,
    val product_id: Int,
    val product_status: String,
    val requests: Int,
    val user_id: Int,
    val visits: Int
)
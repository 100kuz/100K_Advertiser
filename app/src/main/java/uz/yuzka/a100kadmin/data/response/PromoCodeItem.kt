package uz.yuzka.a100kadmin.data.response

import uz.yuzka.a100kadmin.base.Response

typealias PromoCodeResponse = Response<List<PromoCodeItem>>

data class CreatePromoCodeRequest(
    val code: String
)

data class PromoCodeData(
    val data: PromoCodeItem
)

data class PromoCodeItem(
    val code: String,
    val creator_id: Int,
    val id: Int,
    val installs: Int,
    val is_active: Int?,
    val orders: Int,
    val products: Int,
    val type: String,
    val type_label: String,
    val url: String,
    val views: Int
)
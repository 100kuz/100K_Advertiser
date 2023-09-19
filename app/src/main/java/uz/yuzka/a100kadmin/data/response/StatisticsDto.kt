package uz.yuzka.a100kadmin.data.response

import uz.yuzka.a100kadmin.base.Response

typealias StatisticsResponse = Response<List<StatisticsDto>>

data class StatisticsDto(
    val id: Int,
    val name: String,
    val orders_all_count: Int,
    val orders_count: Int,
    val product_title: String?,
    val user_id: Int,
    val visits: Int
)
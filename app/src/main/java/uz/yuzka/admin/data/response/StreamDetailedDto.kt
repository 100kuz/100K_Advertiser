package uz.yuzka.admin.data.response

import uz.yuzka.admin.base.Response

typealias StreamsResponse = Response<List<StreamDetailedDto>>

data class StreamDto(
    val data: StreamDetailedDto
)

data class StreamDetailedDto(
    val charity: Int,
    val created_at: String,
    val discount: Int,
    val id: Int,
    val is_region_disabled: Boolean,
    val link: String?,
    val name: String?,
    val orders_stats: OrdersStats?,
    val product: Product?,
    val product_id: Int,
    val product_status: String,
    val requests: Int,
    val user_id: Int,
    val visits: Int
)
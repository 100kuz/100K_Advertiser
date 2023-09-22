package uz.yuzka.admin.data.response

import retrofit2.Response

data class ChartDto(
    val data: ChartItem
)

typealias ChartResponse = Response<List<ChartItem>>

data class ChartItem(
    val date: String,
    val minus: Int,
    val plus: Int
)
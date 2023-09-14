package uz.yuzka.a100kadmin.data.response

import retrofit2.Response
import uz.yuzka.a100kadmin.data.response.DataX

typealias StatisticsResponse = Response<StatisticsDto>

data class StatisticsDto(
    val `data`: DataX
)
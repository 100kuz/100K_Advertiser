package uz.yuzka.admin.data.response

import uz.yuzka.admin.base.Response

typealias RegionResponse = Response<List<RegionDto>>

data class RegionDto(
    val code: String,
    val delivery_amount: Int,
    val delivery_timer_in_hours: Int,
    val districts: List<District>,
    val id: Int,
    val is_enabled: Boolean,
    val is_pinned: Boolean,
    val name: String
)
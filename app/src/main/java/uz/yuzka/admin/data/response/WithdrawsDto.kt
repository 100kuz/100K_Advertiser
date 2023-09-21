package uz.yuzka.admin.data.response

import uz.yuzka.admin.base.Response

typealias WithdrawsResponse = Response<List<WithdrawsDto>>

data class WithdrawItemData(
    val data: WithdrawsDto
)

data class WithdrawsDto(
    val account: String,
    val amount: Long,
    val created_at: String,
    val created_at_label: String,
    val id: Int,
    val status: String
)
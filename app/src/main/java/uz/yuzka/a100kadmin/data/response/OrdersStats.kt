package uz.yuzka.a100kadmin.data.response

data class OrdersStats(
    val accepted: Int,
    val archived: Int,
    val canceled: Int,
    val delivered: Int,
    val new: Int,
    val pending: Int,
    val sent: Int,
    val spam: Int
)
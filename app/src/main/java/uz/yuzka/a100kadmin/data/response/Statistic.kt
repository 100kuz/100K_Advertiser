package uz.yuzka.a100kadmin.data.response

data class Statistic(
    val cancelled_count: Int,
    val delivered_count: Int,
    val id: Int,
    val in_storage_count: Int,
    val name: String
)
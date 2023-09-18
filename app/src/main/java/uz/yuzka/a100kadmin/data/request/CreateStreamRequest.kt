package uz.yuzka.a100kadmin.data.request

data class CreateStreamRequest(
    val charity: Int?,
    val discount: Int?,
    val name: String,
    val product_id: Int
)
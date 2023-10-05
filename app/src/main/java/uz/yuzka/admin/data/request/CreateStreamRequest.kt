package uz.yuzka.admin.data.request

data class CreateStreamRequest(
    val charity: Int?,
    val name: String,
    val product_id: Int
)
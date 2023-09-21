package uz.yuzka.admin.data.response

data class GetMoneyDto(
    val account: String,
    val amount: Int,
    val created_at: String,
    val created_at_label: String,
    val id: Int,
    val status: String,
    val store_id: Int,
    val updated_at: String
)
package uz.yuzka.admin.data.response

data class District(
    val code: String,
    val country_id: Int,
    val delivery_amount: Int,
    val delivery_timer_in_hours: Int,
    val id: Int,
    val is_enabled: Boolean,
    val name: String
)
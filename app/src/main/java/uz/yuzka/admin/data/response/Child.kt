package uz.yuzka.admin.data.response

data class Child(
    val created_at: String?,
    val description: String?,
    val id: Int,
    val image:String?,
    val is_active: Boolean?,
    val parent_id: Int?,
    val sort_order: Int?,
    val title: String,
    val updated_at: String?
)
package uz.yuzka.a100kadmin.data.response

import uz.yuzka.a100kadmin.base.Response

typealias CategoriesResponse = Response<List<CategoryDto>>

data class CategoryDto(
    val child: List<Child>?,
    val created_at: String?,
    val description: String?,
    val id: Int,
    val image: String?,
    val is_active: Boolean?,
    val parent_id: Int?,
    val sort_order: Int?,
    val title: String?,
    val updated_at: String?
)
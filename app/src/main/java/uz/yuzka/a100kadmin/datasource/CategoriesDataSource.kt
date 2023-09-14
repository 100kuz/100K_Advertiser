package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.network.MainApi
import uz.yuzka.a100kadmin.data.response.CategoryDto
import javax.inject.Inject

class CategoriesDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<CategoryDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryDto> {

        val page: Int = params.key ?: 1

        return try {
            handle {
                api.getCategories(
                    page = page,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    fun create(): PagingSource<Int, CategoryDto> {
        return CategoriesDataSource(api)
    }
}

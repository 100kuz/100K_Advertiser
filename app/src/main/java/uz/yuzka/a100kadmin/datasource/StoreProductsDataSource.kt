package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.data.response.ProductDto
import uz.yuzka.a100kadmin.network.MainApi
import javax.inject.Inject

class StoreProductsDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<ProductDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {

        val page: Int = params.key ?: 1

        return try {
            handle {
                api.getStoreProducts(
                    page = page,
                    category = category
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    fun create(
        category: Int? = null
    ): PagingSource<Int, ProductDto> {
        return StoreProductsDataSource(api).apply {
            this.category = category
        }
    }

    private var category: Int? = null

}

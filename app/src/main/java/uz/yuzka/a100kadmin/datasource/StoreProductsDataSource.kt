package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.network.MainApi
import uz.yuzka.a100kadmin.data.response.ProductDto
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
                    status = status,
                    search = search
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    fun create(
        status: String? = null,
        search: String? = null
    ): PagingSource<Int, ProductDto> {
        return StoreProductsDataSource(api).apply {
            this.status = status
            this.search = search
        }
    }

    private var status: String? = null
    private var search: String? = null

}

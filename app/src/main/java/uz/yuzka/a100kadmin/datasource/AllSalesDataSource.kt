package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.data.response.OrderItem
import uz.yuzka.a100kadmin.network.MainApi
import javax.inject.Inject

class AllSalesDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<OrderItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrderItem> {

        val page: Int = params.key ?: 1

        return try {
            handle {
                api.getSales(
                    page = page,
                    status = status,
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    fun create(
        status: String? = null,
    ): PagingSource<Int, OrderItem> {
        return AllSalesDataSource(api).apply {
            this.status = status
        }
    }

    private var status: String? = null

}

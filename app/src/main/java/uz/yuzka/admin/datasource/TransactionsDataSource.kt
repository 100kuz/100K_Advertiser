package uz.yuzka.admin.datasource

import androidx.paging.PagingSource
import uz.yuzka.admin.base.BasePagingDataSource
import uz.yuzka.admin.data.response.TransactionItem
import uz.yuzka.admin.network.MainApi
import javax.inject.Inject

class TransactionsDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<TransactionItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionItem> {

        val page: Int = params.key ?: 1
        return try {
            handle {
                api.getTransactions(
                    page = page
                )

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun create(): PagingSource<Int, TransactionItem> {
        return TransactionsDataSource(api)
    }

}

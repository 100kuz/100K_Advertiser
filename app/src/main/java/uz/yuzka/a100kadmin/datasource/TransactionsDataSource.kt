package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.data.response.TransactionItem
import uz.yuzka.a100kadmin.network.MainApi
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

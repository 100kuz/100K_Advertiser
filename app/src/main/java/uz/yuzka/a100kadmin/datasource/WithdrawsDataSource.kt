package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.network.MainApi
import uz.yuzka.a100kadmin.data.response.WithdrawsDto
import javax.inject.Inject

class WithdrawsDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<WithdrawsDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WithdrawsDto> {

        val page: Int = params.key ?: 1
        return try {
            handle {
                api.getStoreWithdraws(
                    id = id,
                    page = page
                )

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun create(id: Int): PagingSource<Int, WithdrawsDto> {

        return WithdrawsDataSource(api).apply {
            this.id = id
        }
    }

    private var id: Int = 0
}

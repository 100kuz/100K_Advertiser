package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.data.response.WithdrawsDto
import uz.yuzka.a100kadmin.network.MainApi
import javax.inject.Inject

class WithdrawsDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<WithdrawsDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WithdrawsDto> {

        val page: Int = params.key ?: 1
        return try {
            handle {
                api.getWithdraws(
                    page = page
                )

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun create(): PagingSource<Int, WithdrawsDto> {

        return WithdrawsDataSource(api)
    }

}

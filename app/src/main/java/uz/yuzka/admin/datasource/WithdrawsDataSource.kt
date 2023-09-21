package uz.yuzka.admin.datasource

import androidx.paging.PagingSource
import uz.yuzka.admin.base.BasePagingDataSource
import uz.yuzka.admin.data.response.WithdrawsDto
import uz.yuzka.admin.network.MainApi
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

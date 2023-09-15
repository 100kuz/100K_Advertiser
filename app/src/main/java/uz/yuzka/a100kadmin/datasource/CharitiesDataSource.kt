package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.data.response.CharityItem
import uz.yuzka.a100kadmin.network.MainApi
import javax.inject.Inject

class CharitiesDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<CharityItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharityItem> {

        val page: Int = params.key ?: 1
        return try {
            handle {
                api.getCharities(
                    page = page
                )

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun create(): PagingSource<Int, CharityItem> {
        return CharitiesDataSource(api)
    }

}

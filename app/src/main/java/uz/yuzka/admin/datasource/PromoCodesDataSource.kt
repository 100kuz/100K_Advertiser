package uz.yuzka.admin.datasource

import androidx.paging.PagingSource
import uz.yuzka.admin.base.BasePagingDataSource
import uz.yuzka.admin.data.response.PromoCodeItem
import uz.yuzka.admin.network.MainApi
import javax.inject.Inject

class PromoCodesDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<PromoCodeItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PromoCodeItem> {

        val page: Int = params.key ?: 1

        return try {
            handle {
                api.getPromoCodes(
                    page = page
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    fun create(
    ): PagingSource<Int, PromoCodeItem> {
        return PromoCodesDataSource(api)
    }

}

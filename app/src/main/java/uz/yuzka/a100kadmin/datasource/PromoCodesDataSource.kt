package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.data.response.PromoCodeItem
import uz.yuzka.a100kadmin.network.MainApi
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

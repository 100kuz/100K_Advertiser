package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.base.SaleStatus
import uz.yuzka.a100kadmin.data.response.StatisticsDto
import uz.yuzka.a100kadmin.network.MainApi
import javax.inject.Inject

class StatisticsDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<StatisticsDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StatisticsDto> {

        val page: Int = params.key ?: 1
        return try {
            handle {
                api.getStatistics(
                    page = page,
                    status = status?.status
                )

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun create(status: SaleStatus? = SaleStatus.ALL): PagingSource<Int, StatisticsDto> {

        return StatisticsDataSource(api).apply {
            this.status = status
        }
    }

    private var status: SaleStatus? = SaleStatus.ALL

}

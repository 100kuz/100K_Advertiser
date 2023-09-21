package uz.yuzka.admin.datasource

import androidx.paging.PagingSource
import uz.yuzka.admin.base.BasePagingDataSource
import uz.yuzka.admin.data.response.StreamDetailedDto
import uz.yuzka.admin.network.MainApi
import javax.inject.Inject

class StreamsDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<StreamDetailedDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StreamDetailedDto> {

        val page: Int = params.key ?: 1
        return try {
            handle {
                api.getStreams(
                    page = page
                )

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun create(): PagingSource<Int, StreamDetailedDto> {
        return StreamsDataSource(api)
    }

}

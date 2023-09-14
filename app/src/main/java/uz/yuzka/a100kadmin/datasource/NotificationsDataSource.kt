package uz.yuzka.a100kadmin.datasource

import androidx.paging.PagingSource
import uz.yuzka.a100kadmin.base.BasePagingDataSource
import uz.yuzka.a100kadmin.network.MainApi
import uz.yuzka.a100kadmin.data.response.NotificationDto
import javax.inject.Inject

class NotificationsDataSource @Inject constructor(
    private val api: MainApi
) : BasePagingDataSource<NotificationDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationDto> {

        val page: Int = params.key ?: 1

        return try {
            handle {
                api.getNotifications(
                    page = page
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    fun create(): PagingSource<Int, NotificationDto> {
        return NotificationsDataSource(api)
    }


}

package uz.yuzka.admin.datasource

import androidx.paging.PagingSource
import uz.yuzka.admin.base.BasePagingDataSource
import uz.yuzka.admin.network.MainApi
import uz.yuzka.admin.data.response.NotificationDto
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

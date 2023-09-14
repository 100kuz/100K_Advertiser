package uz.yuzka.a100kadmin.base

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.ConnectException
import java.net.UnknownHostException

abstract class BasePagingDataSource<Value : Any> : PagingSource<Int, Value>() {

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    protected suspend fun handle(
        body: suspend () -> Response<List<Value>>
    ): LoadResult<Int, Value> {

        return try {
            val response: Response<List<Value>> = body()

            handleResource(response)
        } catch (e: Exception) {

            LoadResult.Error(
                when (e) {
                    is NullPointerException -> e
                    is UnknownHostException, is ConnectException ->
                        NetworkException("Check your internet connection. And try again")

                    else -> e
                }
            )
        }

    }

    private fun handleResource(response: Response<List<Value>>): LoadResult<Int, Value> {

        val message = response.message()
        val error = response.errorBody()
        val code = response.code()
        val res: BaseResponse<List<Value>>? = response.body()
        val data = res?.data

        val errorData: BaseErrorResponse? = try {
            Gson().fromJson<BaseErrorResponse?>(
                error?.string(),
                object : TypeToken<BaseErrorResponse>() {}.type
            )

        } catch (e: Exception) {
            null
        }

        return when (code) {
            200 -> {
                if (data != null) {

                    val page = res.meta.currentPage
                    val allPageCount = res.meta.lastPage

                    val nextKey = if (page >= allPageCount) null else page + 1
                    val prevKey = if (page == 1) null else page - 1
                    LoadResult.Page(data, prevKey, nextKey)

                } else LoadResult.Error(NullPointerException(errorData?.message))
            }

            401 -> LoadResult.Error(TokenWrongException("Token expired", errorData?.message))

            400 -> LoadResult.Error(
                TokenNotProvidedException(
                    "Ro'yxatdan o'tish talab qilinadi",
                    errorData?.message
                )
            )

            422 -> LoadResult.Error(
                NotEnoughException(
                    "Bazi maydonlar to'ldirilmadi",
                    errorData?.message
                )
            )

            503 -> LoadResult.Error(ServiceUnavailable("", errorData?.message))

            else -> {

//                Log.i("aksjbvderb", ":" + errorData?.message)
                LoadResult.Error(
                    UnknownErrorException(
                        message,
                        code,
                        errorData?.message
                    )
                )
            }

        }
    }

}
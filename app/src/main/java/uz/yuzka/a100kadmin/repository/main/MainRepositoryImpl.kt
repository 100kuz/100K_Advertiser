package uz.yuzka.a100kadmin.repository.main

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.yuzka.a100kadmin.base.BaseErrorResponse
import uz.yuzka.a100kadmin.base.SaleStatus
import uz.yuzka.a100kadmin.data.request.CreateStreamRequest
import uz.yuzka.a100kadmin.data.request.GetMoneyRequest
import uz.yuzka.a100kadmin.data.response.BalanceResponse
import uz.yuzka.a100kadmin.data.response.CategoryDto
import uz.yuzka.a100kadmin.data.response.CharityItem
import uz.yuzka.a100kadmin.data.response.CreatePromoCodeRequest
import uz.yuzka.a100kadmin.data.response.GetMeDto
import uz.yuzka.a100kadmin.data.response.MessageResponse
import uz.yuzka.a100kadmin.data.response.NotificationDto
import uz.yuzka.a100kadmin.data.response.OrderItem
import uz.yuzka.a100kadmin.data.response.ProductDto
import uz.yuzka.a100kadmin.data.response.ProductItemDto
import uz.yuzka.a100kadmin.data.response.PromoCodeData
import uz.yuzka.a100kadmin.data.response.PromoCodeItem
import uz.yuzka.a100kadmin.data.response.StatisticsDto
import uz.yuzka.a100kadmin.data.response.StreamDetailedDto
import uz.yuzka.a100kadmin.data.response.StreamDto
import uz.yuzka.a100kadmin.data.response.TransactionItem
import uz.yuzka.a100kadmin.data.response.WithdrawItemData
import uz.yuzka.a100kadmin.data.response.WithdrawsDto
import uz.yuzka.a100kadmin.datasource.AllSalesDataSource
import uz.yuzka.a100kadmin.datasource.CategoriesDataSource
import uz.yuzka.a100kadmin.datasource.CharitiesDataSource
import uz.yuzka.a100kadmin.datasource.NotificationsDataSource
import uz.yuzka.a100kadmin.datasource.PromoCodesDataSource
import uz.yuzka.a100kadmin.datasource.StatisticsDataSource
import uz.yuzka.a100kadmin.datasource.StoreProductsDataSource
import uz.yuzka.a100kadmin.datasource.StreamsDataSource
import uz.yuzka.a100kadmin.datasource.TransactionsDataSource
import uz.yuzka.a100kadmin.datasource.WithdrawsDataSource
import uz.yuzka.a100kadmin.network.MainApi
import uz.yuzka.a100kadmin.pref.MyPref
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainApi,
    private val pref: MyPref
) : MainRepository {

    override suspend fun getMe(): Flow<Result<GetMeDto?>> = flow {
        val response = api.getMe()

        if (response.isSuccessful) {
            pref.getMeDto = response.body()
        }
        emit(Result.success(pref.getMeDto))
    }.catch {
        val errorMessage = Throwable("Server bilan muammo bo'ldi")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)

    override suspend fun getProduct(id: Int): Flow<Result<ProductItemDto>> = flow {
        val response = api.getProductById(id)

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                null
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable("Server bilan muammo bo'ldi")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)

    override fun getCategories(): Flow<PagingData<CategoryDto>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    CategoriesDataSource(api).create()
                }, initialKey = 1
            ).flow
        } catch (e: Exception) {
            flow {
                emit(PagingData.empty())
            }
        }
    }

    override fun getSales(
        status: String?,
    ): Flow<PagingData<OrderItem>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    AllSalesDataSource(api).create(status)
                }, initialKey = 1
            ).flow
        } catch (e: Exception) {
            flow {
                emit(PagingData.empty())
            }
        }
    }


    override fun logout(data: LogoutRequest): Flow<Result<MessageResponse>> = flow {

        val result = api.logout(data)

        if (result.isSuccessful) {
            pref.getMeDto = null
            pref.startScreen = false
            pref.token = null
            pref.introScreen = false
            pref.currentStore = -1
            pref.fcmToken = null
            pref.selectedStore = false
            pref.lastFcmToken = null
            emit(Result.success(MessageResponse("Success")))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    result.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                null
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable("Server bilan muammo bo'ldi")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)

    override fun getStoreProducts(
        category: Int?
    ): Flow<PagingData<ProductDto>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    StoreProductsDataSource(api).create(category)
                }, initialKey = 1
            ).flow
        } catch (_: Exception) {
            flow { PagingData.empty<ProductDto>() }
        }
    }

    override fun getStoreWithdraws(
    ): Flow<PagingData<WithdrawsDto>> {

        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    WithdrawsDataSource(api).create()
                }, initialKey = 1
            ).flow
        } catch (_: Exception) {
            flow { PagingData.empty<WithdrawsDto>() }
        }
    }

    override suspend fun getStatistics(status: SaleStatus?): Flow<PagingData<StatisticsDto>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    StatisticsDataSource(api).create(status)
                }, initialKey = 1
            ).flow
        } catch (_: Exception) {
            flow { PagingData.empty<StatisticsDto>() }
        }
    }

    override suspend fun getBalanceStatistics(
        id: Int
    ): Flow<Result<BalanceResponse>> = flow {
/*todo*/
    }


    override suspend fun setDeviceToken(
        data: SetDeviceTokenRequest
    ): Flow<Result<MessageResponse>> = flow {
        val response = api.setDeviceToken(data)

        if (response.isSuccessful) {
            pref.lastFcmToken = data.device_id
            emit(Result.success(response.body()!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                null
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable("Server bilan muammo bo'ldi")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)


    override fun getNotifications(): Flow<PagingData<NotificationDto>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    NotificationsDataSource(api).create()
                }, initialKey = 1
            ).flow
        } catch (e: Exception) {
            flow {
                emit(PagingData.empty())
            }
        }
    }


    override fun getPromoCodes(): Flow<PagingData<PromoCodeItem>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    PromoCodesDataSource(api).create()
                }, initialKey = 1
            ).flow
        } catch (e: Exception) {
            flow {
                emit(PagingData.empty())
            }
        }
    }

    override fun createPromoCode(name: String): Flow<Result<PromoCodeData>> = flow {
        val response = api.createPromoCode(CreatePromoCodeRequest(name))

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                null
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable(it.message)
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)


    override fun getTransactions(): Flow<PagingData<TransactionItem>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    TransactionsDataSource(api).create()
                }, initialKey = 1
            ).flow
        } catch (e: Exception) {
            flow {
                emit(PagingData.empty())
            }
        }
    }


    override fun getCharities(): Flow<PagingData<CharityItem>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    CharitiesDataSource(api).create()
                }, initialKey = 1
            ).flow
        } catch (e: Exception) {
            flow {
                emit(PagingData.empty())
            }
        }
    }

    override fun getMeFromLocal(): Flow<GetMeDto?> = flow {
        emit(pref.getMeDto)
    }.catch {
        emit(null)
    }.flowOn(Dispatchers.IO)


    override fun getStreamById(id: Int): Flow<Result<StreamDto>> = flow {
        val response = api.getStreamById(id)

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                null
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable(it.message)
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)


    override fun createStream(data: CreateStreamRequest): Flow<Result<StreamDto>> = flow {
        val response = api.createStreams(data)

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                null
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable(it.message)
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)

    override fun getStreams(): Flow<PagingData<StreamDetailedDto>> {
        return try {
            Pager(
                config = PagingConfig(
                    pageSize = 4
                ),
                pagingSourceFactory = {
                    StreamsDataSource(api).create()
                }, initialKey = 1
            ).flow
        } catch (_: Exception) {
            flow {
                emit(PagingData.empty())
            }
        }
    }

    override fun cancelWithdraw(id: Int): Flow<Result<WithdrawsDto>> = flow {
        val response = api.cancelWithdraw(id)

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!.data))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                null
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable(it.message)
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)


    override fun createWithdraw(data: GetMoneyRequest): Flow<Result<WithdrawItemData>> = flow {
        val response = api.createWithdraw(data)

        if (response.isSuccessful) {
            emit(Result.success(response.body()!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                null
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable(it.message)
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)


}

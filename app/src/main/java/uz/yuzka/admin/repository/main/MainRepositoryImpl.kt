package uz.yuzka.admin.repository.main

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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import uz.yuzka.admin.base.BaseErrorResponse
import uz.yuzka.admin.base.SaleStatus
import uz.yuzka.admin.data.request.CreateStreamRequest
import uz.yuzka.admin.data.request.GetMoneyRequest
import uz.yuzka.admin.data.request.SetDeviceTokenRequest
import uz.yuzka.admin.data.response.BalanceResponse
import uz.yuzka.admin.data.response.CategoryDto
import uz.yuzka.admin.data.response.CharityItem
import uz.yuzka.admin.data.response.ChartItem
import uz.yuzka.admin.data.response.CreatePromoCodeRequest
import uz.yuzka.admin.data.response.GetMeDto
import uz.yuzka.admin.data.response.MessageResponse
import uz.yuzka.admin.data.response.NotificationDto
import uz.yuzka.admin.data.response.OrderItem
import uz.yuzka.admin.data.response.ProductDto
import uz.yuzka.admin.data.response.ProductItemDto
import uz.yuzka.admin.data.response.PromoCodeData
import uz.yuzka.admin.data.response.PromoCodeItem
import uz.yuzka.admin.data.response.RegionDto
import uz.yuzka.admin.data.response.StatisticsDto
import uz.yuzka.admin.data.response.StreamDetailedDto
import uz.yuzka.admin.data.response.StreamDto
import uz.yuzka.admin.data.response.TransactionItem
import uz.yuzka.admin.data.response.WithdrawItemData
import uz.yuzka.admin.data.response.WithdrawsDto
import uz.yuzka.admin.datasource.AllSalesDataSource
import uz.yuzka.admin.datasource.CategoriesDataSource
import uz.yuzka.admin.datasource.CharitiesDataSource
import uz.yuzka.admin.datasource.NotificationsDataSource
import uz.yuzka.admin.datasource.PromoCodesDataSource
import uz.yuzka.admin.datasource.StatisticsDataSource
import uz.yuzka.admin.datasource.StoreProductsDataSource
import uz.yuzka.admin.datasource.StreamsDataSource
import uz.yuzka.admin.datasource.TransactionsDataSource
import uz.yuzka.admin.datasource.WithdrawsDataSource
import uz.yuzka.admin.network.MainApi
import uz.yuzka.admin.pref.MyPref
import uz.yuzka.seller.data.request.LogoutRequest
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


    override fun logout(): Flow<Result<MessageResponse>> = flow {

        api.logout(LogoutRequest(pref.lastFcmToken ?: ""))

        pref.getMeDto = null
        pref.startScreen = false
        pref.token = null
        pref.introScreen = false
        pref.currentStore = -1
        pref.fcmToken = null
        pref.selectedStore = false
        pref.lastFcmToken = null

        emit(Result.success(MessageResponse("Success")))

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

            val getMe = api.getMe()
            if (getMe.isSuccessful) {
                pref.getMeDto = getMe.body()
            }

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

            val getMe = api.getMe()
            if (getMe.isSuccessful) {
                pref.getMeDto = getMe.body()
            }

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


    override fun getLocations(): Flow<Result<List<RegionDto>>> = flow {
        val response = api.getLocations()

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


    override fun updateUser(
        name: String,
        surname: String,
        regionId: Int,
        districtId: Int,
        address: String
    ): Flow<Result<GetMeDto>> = flow {

        val name1 = (name).toRequestBody("text/plain".toMediaType())
        val surname1 = (surname).toRequestBody("text/plain".toMediaType())
        val address1 = (address).toRequestBody("text/plain".toMediaType())
        val regionId1 =
            (regionId.toString()).toRequestBody("text/plain".toMediaType())
        val gender =
            ("male").toRequestBody("text/plain".toMediaType())
        val districtId1 =
            (districtId.toString()).toRequestBody("text/plain".toMediaType())

        val response = api.updateUser(name1, surname1, regionId1, districtId1, address1, gender)

        if (response.isSuccessful) {
            pref.getMeDto = response.body()
            emit(Result.success(pref.getMeDto!!))
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

    override fun deleteStream(id: Int): Flow<Result<MessageResponse>> = flow {
        val response = api.deleteStream(id)

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


    override fun getTransactionStats(): Flow<Result<List<ChartItem>>> = flow {
        val response = api.getTransactionStatistics()

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


    override fun generatePost(id: Int): Flow<Result<MessageResponse>> = flow {
        val response = api.generatePost(id)

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

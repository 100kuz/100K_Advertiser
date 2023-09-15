package uz.yuzka.a100kadmin.usecase.main

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.a100kadmin.data.response.BalanceResponse
import uz.yuzka.a100kadmin.data.response.CategoryDto
import uz.yuzka.a100kadmin.data.response.CharityItem
import uz.yuzka.a100kadmin.data.response.GetMeDto
import uz.yuzka.a100kadmin.data.response.GetMoneyResponse
import uz.yuzka.a100kadmin.data.response.MessageResponse
import uz.yuzka.a100kadmin.data.response.NotificationDto
import uz.yuzka.a100kadmin.data.response.OrderItem
import uz.yuzka.a100kadmin.data.response.ProductDto
import uz.yuzka.a100kadmin.data.response.ProductItemDto
import uz.yuzka.a100kadmin.data.response.PromoCodeData
import uz.yuzka.a100kadmin.data.response.PromoCodeItem
import uz.yuzka.a100kadmin.data.response.StatisticsDto
import uz.yuzka.a100kadmin.data.response.TransactionItem
import uz.yuzka.a100kadmin.data.response.WithdrawsDto
import uz.yuzka.seller.data.request.GetMoneyRequest
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest

interface MainUseCase {
    suspend fun getMe(): Flow<Result<GetMeDto?>>

    suspend fun getProductById(id: Int): Flow<Result<ProductItemDto>>

    fun getSales(
        status: String? = null,
    ): Flow<PagingData<OrderItem>>

    fun getCategories(): Flow<PagingData<CategoryDto>>

    fun logout(data: LogoutRequest): Flow<Result<MessageResponse>>

    fun getStoreWithdraws(
        store: Int
    ): Flow<PagingData<WithdrawsDto>>

    fun getStoreProducts(
        status: String?,
        search: String?
    ): Flow<PagingData<ProductDto>>

    suspend fun getMoney(
        store: Int,
        getMoneyRequest: GetMoneyRequest
    ): Flow<Result<GetMoneyResponse>>

    suspend fun getStatistics(
        store: Int
    ): Flow<Result<StatisticsDto>>

    suspend fun getBalanceStatistics(
        id: Int
    ): Flow<Result<BalanceResponse>>

    suspend fun setDeviceToken(
        data: SetDeviceTokenRequest
    ): Flow<Result<MessageResponse>>

    fun getNotifications(): Flow<PagingData<NotificationDto>>

    fun getPromoCodes(): Flow<PagingData<PromoCodeItem>>

    fun getTransactions(): Flow<PagingData<TransactionItem>>

    fun createPromoCode(name: String): Flow<Result<PromoCodeData>>

    fun getCharities(): Flow<PagingData<CharityItem>>

}

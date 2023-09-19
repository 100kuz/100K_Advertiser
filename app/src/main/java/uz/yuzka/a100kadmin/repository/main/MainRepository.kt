package uz.yuzka.a100kadmin.repository.main

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.a100kadmin.base.SaleStatus
import uz.yuzka.a100kadmin.data.request.CreateStreamRequest
import uz.yuzka.a100kadmin.data.request.GetMoneyRequest
import uz.yuzka.a100kadmin.data.response.BalanceResponse
import uz.yuzka.a100kadmin.data.response.CategoryDto
import uz.yuzka.a100kadmin.data.response.CharityItem
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
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest

interface MainRepository {
    suspend fun getMe(): Flow<Result<GetMeDto?>>

    suspend fun getProduct(id: Int): Flow<Result<ProductItemDto>>

//    fun getAllIncomes(): Flow<PagingData<IncomesItemDto>>

    fun getCategories(): Flow<PagingData<CategoryDto>>

    fun getSales(
        status: String? = null
    ): Flow<PagingData<OrderItem>>

    fun logout(data: LogoutRequest): Flow<Result<MessageResponse>>

    fun getStoreWithdraws(
    ): Flow<PagingData<WithdrawsDto>>

    fun getStoreProducts(
        category: Int?
    ): Flow<PagingData<ProductDto>>

    suspend fun getStatistics(status: SaleStatus? = SaleStatus.ALL): Flow<PagingData<StatisticsDto>>

    suspend fun getBalanceStatistics(
        id: Int
    ): Flow<Result<BalanceResponse>>

    suspend fun setDeviceToken(
        data: SetDeviceTokenRequest
    ): Flow<Result<MessageResponse>>

    fun getNotifications(): Flow<PagingData<NotificationDto>>

    fun getPromoCodes(): Flow<PagingData<PromoCodeItem>>

    fun createPromoCode(name: String): Flow<Result<PromoCodeData>>

    fun getTransactions(): Flow<PagingData<TransactionItem>>

    fun getCharities(): Flow<PagingData<CharityItem>>

    fun getMeFromLocal(): Flow<GetMeDto?>

    fun getStreamById(id: Int): Flow<Result<StreamDto>>

    fun getStreams(): Flow<PagingData<StreamDetailedDto>>

    fun createStream(data: CreateStreamRequest): Flow<Result<StreamDto>>

    fun cancelWithdraw(id: Int): Flow<Result<WithdrawsDto>>

    fun createWithdraw(data: GetMoneyRequest): Flow<Result<WithdrawItemData>>

}
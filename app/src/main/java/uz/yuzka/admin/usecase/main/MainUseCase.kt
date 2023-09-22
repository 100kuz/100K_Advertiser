package uz.yuzka.admin.usecase.main

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.admin.base.SaleStatus
import uz.yuzka.admin.data.request.CreateStreamRequest
import uz.yuzka.admin.data.request.GetMoneyRequest
import uz.yuzka.admin.data.request.SetDeviceTokenRequest
import uz.yuzka.admin.data.response.BalanceResponse
import uz.yuzka.admin.data.response.CategoryDto
import uz.yuzka.admin.data.response.CharityItem
import uz.yuzka.admin.data.response.ChartItem
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

interface MainUseCase {
    suspend fun getMe(): Flow<Result<GetMeDto?>>

    suspend fun getMeFromLocal(): Flow<GetMeDto?>

    suspend fun getProductById(id: Int): Flow<Result<ProductItemDto>>

    fun getSales(
        status: String? = null,
    ): Flow<PagingData<OrderItem>>

    fun getCategories(): Flow<PagingData<CategoryDto>>

    fun logout(): Flow<Result<MessageResponse>>

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

    fun getTransactions(): Flow<PagingData<TransactionItem>>

    fun createPromoCode(name: String): Flow<Result<PromoCodeData>>

    fun getCharities(): Flow<PagingData<CharityItem>>

    fun getStreamById(id: Int): Flow<Result<StreamDto>>

    fun getStreams(): Flow<PagingData<StreamDetailedDto>>

    fun createStream(data: CreateStreamRequest): Flow<Result<StreamDto>>

    fun cancelWithdraw(id: Int): Flow<Result<WithdrawsDto>>

    fun createWithdraw(data: GetMoneyRequest): Flow<Result<WithdrawItemData>>

    fun getLocations(): Flow<Result<List<RegionDto>>>

    fun updateUser(
        name: String,
        surname: String,
        regionId: Int,
        districtId: Int,
        address: String
    ): Flow<Result<GetMeDto>>

    fun deleteStream(id: Int): Flow<Result<MessageResponse>>

    fun getTransactionStats(): Flow<Result<List<ChartItem>>>

    fun generatePost(id: Int): Flow<Result<MessageResponse>>
}

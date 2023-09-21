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
import uz.yuzka.admin.repository.main.MainRepository
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(
    private val repository: MainRepository
) : MainUseCase {
    override suspend fun getMe(): Flow<Result<GetMeDto?>> {
        return repository.getMe()
    }

    override suspend fun getMeFromLocal(): Flow<GetMeDto?> {
        return repository.getMeFromLocal()
    }
//
//    override fun getProducts(): Flow<PagingData<ProductDto>> {
//        return repository.getProducts()
//    }

    override suspend fun getProductById(id: Int): Flow<Result<ProductItemDto>> {
        return repository.getProduct(id)
    }

    override fun getSales(
        status: String?,
    ): Flow<PagingData<OrderItem>> {
        return repository.getSales(status)
    }

    override fun getCategories(): Flow<PagingData<CategoryDto>> {
        return repository.getCategories()
    }

    override fun logout(): Flow<Result<MessageResponse>> {
        return repository.logout()
    }

    override fun getStoreWithdraws(
    ): Flow<PagingData<WithdrawsDto>> =
        repository.getStoreWithdraws()

    override fun getStoreProducts(
        category: Int?
    ): Flow<PagingData<ProductDto>> = repository.getStoreProducts(category)

    override suspend fun getStatistics(status: SaleStatus?): Flow<PagingData<StatisticsDto>> =
        repository.getStatistics(status)

    override suspend fun getBalanceStatistics(
        id: Int
    ): Flow<Result<BalanceResponse>> = repository.getBalanceStatistics(id)

    override suspend fun setDeviceToken(
        data: SetDeviceTokenRequest
    ): Flow<Result<MessageResponse>> = repository.setDeviceToken(data)

    override fun getNotifications(): Flow<PagingData<NotificationDto>> =
        repository.getNotifications()

    override fun getPromoCodes(): Flow<PagingData<PromoCodeItem>> =
        repository.getPromoCodes()

    override fun getTransactions(): Flow<PagingData<TransactionItem>> =
        repository.getTransactions()

    override fun createPromoCode(name: String): Flow<Result<PromoCodeData>> =
        repository.createPromoCode(name)

    override fun getCharities(): Flow<PagingData<CharityItem>> = repository.getCharities()

    override fun getStreamById(id: Int): Flow<Result<StreamDto>> = repository.getStreamById(id)

    override fun getStreams(): Flow<PagingData<StreamDetailedDto>> = repository.getStreams()

    override fun createStream(data: CreateStreamRequest): Flow<Result<StreamDto>> =
        repository.createStream(data)

    override fun cancelWithdraw(id: Int): Flow<Result<WithdrawsDto>> = repository.cancelWithdraw(id)

    override fun createWithdraw(data: GetMoneyRequest): Flow<Result<WithdrawItemData>> =
        repository.createWithdraw(data)

    override fun getLocations(): Flow<Result<List<RegionDto>>> = repository.getLocations()

    override fun updateUser(
        name: String,
        surname: String,
        regionId: Int,
        districtId: Int,
        address: String
    ): Flow<Result<GetMeDto>> = repository.updateUser(name, surname, regionId, districtId, address)

    override fun deleteStream(id: Int): Flow<Result<MessageResponse>> = repository.deleteStream(id)
}

package uz.yuzka.a100kadmin.usecase.main

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.a100kadmin.data.request.CreateStreamRequest
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
import uz.yuzka.a100kadmin.repository.main.MainRepository
import uz.yuzka.a100kadmin.data.request.GetMoneyRequest
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(private val repository: MainRepository) : MainUseCase {
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

    override fun logout(data: LogoutRequest): Flow<Result<MessageResponse>> {
        return repository.logout(data)
    }

    override fun getStoreWithdraws(
    ): Flow<PagingData<WithdrawsDto>> =
        repository.getStoreWithdraws()

    override fun getStoreProducts(
        category: Int?
    ): Flow<PagingData<ProductDto>> = repository.getStoreProducts(category)

    override suspend fun getStatistics(
        store: Int
    ): Flow<Result<StatisticsDto>> = repository.getStatistics(store)

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

}

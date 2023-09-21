package uz.yuzka.admin.ui.viewModel.home

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.admin.base.SaleStatus
import uz.yuzka.admin.data.response.CharityItem
import uz.yuzka.admin.data.response.GetMeDto
import uz.yuzka.admin.data.response.OrderItem
import uz.yuzka.admin.data.response.PromoCodeItem
import uz.yuzka.admin.data.response.TransactionItem

interface HomeViewModel {

    val progressFlow: Flow<Boolean>
    val getMeProgressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val sales: Flow<PagingData<OrderItem>>
    val status: LiveData<SaleStatus>
    val transactions: Flow<PagingData<TransactionItem>>
    val charities: Flow<PagingData<CharityItem>>
    val promoCodes: Flow<PagingData<PromoCodeItem>>
    val getMeData: Flow<GetMeDto>

    val hasLoadedPromoCodes: LiveData<Boolean>
    val hasLoadedTransactions: LiveData<Boolean>
    val hasLoadedCharities: LiveData<Boolean>
    val hasLoadedOrders: LiveData<Boolean>
    val hasLoadedGetMe: LiveData<Boolean>

    fun selectOrderStatus(status: SaleStatus)

    fun getOrders(status: SaleStatus)

    fun getPromoCodes()

    fun getTransactions()

    fun getCharities()

    fun gotError()

    fun getMeFromLocal()

    fun getMe()

}
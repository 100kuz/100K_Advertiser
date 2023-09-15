package uz.yuzka.a100kadmin.ui.viewModel.home

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.a100kadmin.base.SaleStatus
import uz.yuzka.a100kadmin.data.response.CharityItem
import uz.yuzka.a100kadmin.data.response.OrderItem
import uz.yuzka.a100kadmin.data.response.PromoCodeItem
import uz.yuzka.a100kadmin.data.response.TransactionItem

interface HomeViewModel {

    val progressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val sales: Flow<PagingData<OrderItem>>
    val status: LiveData<SaleStatus>
    val transactions: Flow<PagingData<TransactionItem>>
    val charities: Flow<PagingData<CharityItem>>
    val promoCodes: Flow<PagingData<PromoCodeItem>>

    val hasLoadedPromoCodes: LiveData<Boolean>
    val hasLoadedTransactions: LiveData<Boolean>
    val hasLoadedCharities: LiveData<Boolean>
    val hasLoadedOrders: LiveData<Boolean>

    fun selectOrderStatus(status: SaleStatus)

    fun getOrders(status: SaleStatus)

    fun getPromoCodes()

    fun getTransactions()

    fun getCharities()

    fun gotError()

}
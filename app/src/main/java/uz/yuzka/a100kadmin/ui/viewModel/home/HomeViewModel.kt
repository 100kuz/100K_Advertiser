package uz.yuzka.a100kadmin.ui.viewModel.home

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.a100kadmin.base.SaleStatus
import uz.yuzka.a100kadmin.data.response.BalanceSto
import uz.yuzka.a100kadmin.data.response.OrderItem
import uz.yuzka.a100kadmin.data.response.PromoCodeItem

interface HomeViewModel {

    val progressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val sales: Flow<PagingData<OrderItem>>
    val status: LiveData<SaleStatus>
    val balanceHistory: Flow<BalanceSto>
    val promoCodes: Flow<PagingData<PromoCodeItem>>

    val hasLoadedPromoCodes: LiveData<Boolean>
    val hasLoadedOrders: LiveData<Boolean>

    fun selectOrderStatus(status: SaleStatus)

    fun getOrders(status: SaleStatus)

    fun getPromoCodes()

    fun gotError()

}
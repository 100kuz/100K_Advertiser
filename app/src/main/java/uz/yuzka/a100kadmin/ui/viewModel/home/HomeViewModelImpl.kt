package uz.yuzka.a100kadmin.ui.viewModel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.yuzka.a100kadmin.base.SaleStatus
import uz.yuzka.a100kadmin.data.response.BalanceSto
import uz.yuzka.a100kadmin.data.response.OrderItem
import uz.yuzka.a100kadmin.data.response.PromoCodeItem
import uz.yuzka.a100kadmin.usecase.main.MainUseCase
import uz.yuzka.a100kadmin.utils.eventValueFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val useCase: MainUseCase
) : HomeViewModel, ViewModel() {
    override val progressFlow = eventValueFlow<Boolean>()
    override val errorFlow = eventValueFlow<String?>()
    override val sales = eventValueFlow<PagingData<OrderItem>>()
    override val status = MutableLiveData<SaleStatus>(SaleStatus.ALL)
    override val balanceHistory: Flow<BalanceSto>
        get() = TODO("Not yet implemented")
    override val promoCodes = eventValueFlow<PagingData<PromoCodeItem>>()

    override val hasLoadedPromoCodes = MutableLiveData(false)
    override val hasLoadedOrders = MutableLiveData(false)

    override fun selectOrderStatus(status: SaleStatus) {
        this.status.value = status
    }

    override fun getOrders(status: SaleStatus) {
        viewModelScope.launch {
            this@HomeViewModelImpl.status.value = status
            progressFlow.emit(true)
            useCase.getSales(status.status).cachedIn(viewModelScope).onEach {
                sales.emit(it)
                hasLoadedOrders.value = true
            }.cachedIn(viewModelScope).launchIn(viewModelScope)
            delay(1000)
            progressFlow.emit(false)
        }
    }

    override fun getPromoCodes() {
        viewModelScope.launch {
            progressFlow.emit(true)
            useCase.getPromoCodes().cachedIn(viewModelScope).onEach {
                promoCodes.emit(it)
                hasLoadedPromoCodes.value = true
            }.cachedIn(viewModelScope).launchIn(viewModelScope)
            delay(1000)
            progressFlow.emit(false)
        }
    }

    override fun gotError() {
        viewModelScope.launch {
            errorFlow.emit(null)
        }
    }


}
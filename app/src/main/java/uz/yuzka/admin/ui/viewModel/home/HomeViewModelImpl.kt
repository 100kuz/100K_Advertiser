package uz.yuzka.admin.ui.viewModel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.yuzka.admin.base.SaleStatus
import uz.yuzka.admin.data.response.CharityItem
import uz.yuzka.admin.data.response.GetMeDto
import uz.yuzka.admin.data.response.OrderItem
import uz.yuzka.admin.data.response.PromoCodeItem
import uz.yuzka.admin.data.response.TransactionItem
import uz.yuzka.admin.usecase.main.MainUseCase
import uz.yuzka.admin.utils.eventValueFlow
import uz.yuzka.admin.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val useCase: MainUseCase
) : HomeViewModel, ViewModel() {
    override val progressFlow = eventValueFlow<Boolean>()
    override val getMeProgressFlow = eventValueFlow<Boolean>()
    override val errorFlow = eventValueFlow<String?>()
    override val sales = eventValueFlow<PagingData<OrderItem>>()
    override val status = MutableLiveData<SaleStatus>(SaleStatus.ALL)
    override val transactions = eventValueFlow<PagingData<TransactionItem>>()
    override val charities = eventValueFlow<PagingData<CharityItem>>()
    override val promoCodes = eventValueFlow<PagingData<PromoCodeItem>>()
    override val getMeData = eventValueFlow<GetMeDto>()

    override val hasLoadedPromoCodes = MutableLiveData(false)
    override val hasLoadedTransactions = MutableLiveData(false)
    override val hasLoadedCharities = MutableLiveData(false)
    override val hasLoadedOrders = MutableLiveData(false)
    override val hasLoadedGetMe = MutableLiveData(false)


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

    override fun getTransactions() {
        viewModelScope.launch {
            progressFlow.emit(true)
            useCase.getTransactions().cachedIn(viewModelScope).onEach {
                transactions.emit(it)
                hasLoadedTransactions.value = true
            }.cachedIn(viewModelScope).launchIn(viewModelScope)
            delay(1000)
            progressFlow.emit(false)
        }
    }

    override fun getCharities() {
        viewModelScope.launch {
            progressFlow.emit(true)
            useCase.getCharities().cachedIn(viewModelScope).onEach {
                charities.emit(it)
                hasLoadedCharities.value = true
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

    override fun getMeFromLocal() {
        viewModelScope.launch {
            useCase.getMeFromLocal().onEach {
                it?.let { getMeData.emit(it) }
            }.launchIn(viewModelScope)
        }
    }

    override fun getMe() {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            getMeProgressFlow.emit(true)
            useCase.getMe().onEach {
                it.onSuccess { res ->
                    hasLoadedGetMe.value = true
                    res?.let { dto -> getMeData.emit(dto) }
                }
            }.launchIn(viewModelScope)
            delay(1000)
            getMeProgressFlow.emit(false)
        }
    }

}
package uz.yuzka.a100kadmin.ui.viewModel.withdraws

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
import uz.yuzka.a100kadmin.data.request.GetMoneyRequest
import uz.yuzka.a100kadmin.data.response.GetMeDto
import uz.yuzka.a100kadmin.data.response.WithdrawsDto
import uz.yuzka.a100kadmin.usecase.main.MainUseCase
import uz.yuzka.a100kadmin.utils.eventValueFlow
import uz.yuzka.a100kadmin.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class WithdrawsViewModelImpl @Inject constructor(
    private val useCase: MainUseCase
) : WithdrawsViewModel, ViewModel() {
    override val progressFlow = eventValueFlow<Boolean>()
    override val errorFlow = eventValueFlow<String?>()
    override val withdraws = eventValueFlow<PagingData<WithdrawsDto>>()
    override val getMeData = eventValueFlow<GetMeDto>()

    override val hasLoadedWithdraws = MutableLiveData(false)
    override val hasLoadedUser = MutableLiveData(false)
    override val hasCanceled = eventValueFlow<Boolean>()
    override val hasCreatedWithdraw = eventValueFlow<Boolean>()

    override fun getWithdraws() {
        viewModelScope.launch {
            progressFlow.emit(true)
            useCase.getStoreWithdraws().cachedIn(viewModelScope).onEach {
                withdraws.emit(it)
                hasLoadedWithdraws.value = true
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
            progressFlow.emit(true)
            useCase.getMe().onEach {
                it.onSuccess { res ->
                    res?.let { dto -> getMeData.emit(dto) }
                    hasLoadedUser.value = true
                }
            }.launchIn(viewModelScope)
            delay(1000)
            progressFlow.emit(false)
        }
    }

    override fun cancelWithdraw(id: Int) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan aloqa yo'q!")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        viewModelScope.launch {
            useCase.cancelWithdraw(id).onEach {
                progressFlow.emit(false)
                it.onSuccess {
                    hasCanceled.emit(true)
                }
                it.onFailure { err ->
                    errorFlow.emit(err.message)
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun gotCancel() {
        viewModelScope.launch {
            hasCanceled.emit(false)
        }
    }

    override fun createWithdraw(data: GetMoneyRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan aloqa yo'q!")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        viewModelScope.launch {
            useCase.createWithdraw(data).onEach {
                progressFlow.emit(false)
                it.onSuccess {
                    hasCreatedWithdraw.emit(true)
                }
                it.onFailure { err ->
                    errorFlow.emit(err.message)
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun gotCreateSuccess() {
        viewModelScope.launch {
            hasCreatedWithdraw.emit(false)
        }
    }

}
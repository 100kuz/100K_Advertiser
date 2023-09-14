package uz.yuzka.a100kadmin.ui.viewModel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.yuzka.a100kadmin.base.Status
import uz.yuzka.a100kadmin.usecase.main.MainUseCase
import uz.yuzka.a100kadmin.utils.eventValueFlow
import uz.yuzka.a100kadmin.utils.isConnected
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest
import uz.yuzka.a100kadmin.data.response.GetMeDto
import uz.yuzka.a100kadmin.data.response.ProductDto
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val useCase: MainUseCase,
) : ViewModel(),
    MainViewModel {
    override val errorFlow = eventValueFlow<String?>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val getMeFlow = eventValueFlow<GetMeDto>()
    override val firstInit = MutableLiveData(false)
    override val logoutFlow = eventValueFlow<Boolean?>()
    override val logoutError = eventValueFlow<String?>()
    override val productItemDto = MutableLiveData<ProductDto?>()

    override suspend fun getMe() {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo bo'ldi")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.getMe().onEach {
            it.onSuccess { dto ->
                progressFlow.emit(false)
                dto?.let { getMeFlow.emit(dto) }
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }


    override fun getStoreProducts(status: Status, search: String?) {
        useCase.getStoreProducts(
            status.status,
            search
        ).cachedIn(viewModelScope).onEach {

        }.launchIn(viewModelScope)
    }


    override fun refresh() {
        viewModelScope.launch {
            progressFlow.emit(true)
            delay(1500)
            progressFlow.emit(false)
        }
    }


    override fun getProductById(id: Int) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo bo'ldi")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        viewModelScope.launch {
            useCase.getProductById(id).onEach {
                it.onSuccess { dto ->
                    progressFlow.emit(false)
                    productItemDto.value = dto.data
                }
                it.onFailure { error ->
                    progressFlow.emit(false)
                    errorFlow.emit(error.message)
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun search(value: String?) {
        viewModelScope.launch {

        }
    }

    override fun logout(data: LogoutRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                logoutError.emit("Internet bilan aloqa y'oq!")
            }
            return
        }
        useCase.logout(data).onEach {
            it.onSuccess {
                logoutFlow.emit(true)
                logoutError.emit(
                    null
                )
            }
            it.onFailure { err ->
                logoutError.emit(err.message)
                logoutFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    override fun setDeviceToken(data: SetDeviceTokenRequest) {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            useCase.setDeviceToken(data).onEach {
                it.onSuccess {

                }
                it.onFailure {

                }
            }.launchIn(viewModelScope)
        }
    }

    override fun gotError() {
        viewModelScope.launch {
            errorFlow.emit(null)
        }
    }

    override fun setScroll(x: Int, y: Int) {
        viewModelScope.launch {

        }
    }


}

package uz.yuzka.admin.ui.viewModel.createpromocode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.yuzka.admin.data.response.PromoCodeItem
import uz.yuzka.admin.usecase.main.MainUseCase
import uz.yuzka.admin.utils.eventValueFlow
import uz.yuzka.admin.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class CreatePromoCodeViewModelImpl @Inject constructor(
    private val useCase: MainUseCase
) :
    CreatePromoCodeViewModel,
    ViewModel() {
    override val progressFlow = eventValueFlow<Boolean>()
    override val errorFlow = eventValueFlow<String?>()

    override val hasCreatedPromoCode = eventValueFlow<PromoCodeItem?>()

    override fun createPromoCode(name: String) {
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
            useCase.createPromoCode(name).onEach {
                progressFlow.emit(false)
                it.onSuccess { res ->
                    hasCreatedPromoCode.emit(res.data)
                }
                it.onFailure { err ->
                    errorFlow.emit(err.message)
                }
            }.launchIn(viewModelScope)
        }

    }

    override fun gotError() {
        viewModelScope.launch {
            errorFlow.emit(null)
        }
    }

    override fun clearData() {
        viewModelScope.launch {
            hasCreatedPromoCode.emit(null)
        }
    }

    override fun setData(promoCodeData: PromoCodeItem) {
        viewModelScope.launch {
            hasCreatedPromoCode.emit(promoCodeData)
        }
    }

}
package uz.yuzka.admin.ui.viewModel.statistics

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
import uz.yuzka.admin.data.response.GetMeDto
import uz.yuzka.admin.data.response.StatisticsDto
import uz.yuzka.admin.usecase.main.MainUseCase
import uz.yuzka.admin.utils.eventValueFlow
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModelImpl @Inject constructor(
    private val useCase: MainUseCase
) : StatisticsViewModel, ViewModel() {

    override val progressFlow = eventValueFlow<Boolean>()
    override val errorFlow = eventValueFlow<String?>()
    override val status = MutableLiveData<SaleStatus>(SaleStatus.ALL)
    override val statistics = eventValueFlow<PagingData<StatisticsDto>>()
    override val getMeFlow = eventValueFlow<GetMeDto>()

    override val hasLoadedStatistics = MutableLiveData(false)

    override fun selectOrderStatus(status: SaleStatus) {
        this.status.value = status
    }

    override fun getStatistics(status: SaleStatus) {
        viewModelScope.launch {
            progressFlow.emit(true)
            useCase.getStatistics(status).cachedIn(viewModelScope).onEach {
                statistics.emit(it)
                this@StatisticsViewModelImpl.status.value = status
                hasLoadedStatistics.value = true
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
                it?.let { getMeFlow.emit(it) }
            }.launchIn(viewModelScope)
        }
    }


}
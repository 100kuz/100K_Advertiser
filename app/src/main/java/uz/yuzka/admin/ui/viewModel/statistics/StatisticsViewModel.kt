package uz.yuzka.admin.ui.viewModel.statistics

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.admin.base.SaleStatus
import uz.yuzka.admin.data.response.StatisticsDto

interface StatisticsViewModel {

    val progressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val statistics: Flow<PagingData<StatisticsDto>>
    val status: LiveData<SaleStatus>

    val hasLoadedStatistics: LiveData<Boolean>

    fun selectOrderStatus(status: SaleStatus)

    fun getStatistics(status: SaleStatus)

    fun gotError()

}
package uz.yuzka.admin.ui.viewModel.withdraws

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.admin.data.response.GetMeDto
import uz.yuzka.admin.data.response.WithdrawsDto
import uz.yuzka.admin.data.request.GetMoneyRequest

interface WithdrawsViewModel {

    val progressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val getMeData: Flow<GetMeDto>
    val withdraws: Flow<PagingData<WithdrawsDto>>
    val hasLoadedWithdraws: LiveData<Boolean>
    val hasLoadedUser: LiveData<Boolean>
    val hasCanceled: Flow<Boolean>
    val hasCreatedWithdraw: Flow<Boolean>

    fun getWithdraws()

    fun gotError()

    fun getMeFromLocal()

    fun getMe()

    fun cancelWithdraw(id: Int)

    fun gotCancel()

    fun createWithdraw(data: GetMoneyRequest)

    fun gotCreateSuccess()

}
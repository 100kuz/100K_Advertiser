package uz.yuzka.a100kadmin.ui.viewModel.main

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.a100kadmin.base.Status
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest
import uz.yuzka.a100kadmin.data.response.GetMeDto
import uz.yuzka.a100kadmin.data.response.ProductDto

interface MainViewModel {

    val errorFlow: Flow<String?>
    val progressFlow: Flow<Boolean>
    val getMeFlow: Flow<GetMeDto>
    val firstInit: LiveData<Boolean>
    val productItemDto: LiveData<ProductDto?>
    val logoutFlow: Flow<Boolean?>
    val logoutError: Flow<String?>

    suspend fun getMe()

    fun getStoreProducts(status: Status, search: String? = null)

    fun refresh()

    fun getProductById(id: Int)

    fun search(value: String?)

    fun logout(data: LogoutRequest)

    fun setDeviceToken(data: SetDeviceTokenRequest)

    fun gotError()

    fun setScroll(x: Int, y: Int)

}
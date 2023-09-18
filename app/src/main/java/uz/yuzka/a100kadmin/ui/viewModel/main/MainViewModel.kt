package uz.yuzka.a100kadmin.ui.viewModel.main

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.a100kadmin.data.request.CreateStreamRequest
import uz.yuzka.a100kadmin.data.response.CategoryDto
import uz.yuzka.a100kadmin.data.response.GetMeDto
import uz.yuzka.a100kadmin.data.response.ProductDto
import uz.yuzka.a100kadmin.data.response.StreamDetailedDto
import uz.yuzka.a100kadmin.data.response.StreamDto
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest

interface MainViewModel {

    val errorFlow: Flow<String?>
    val progressFlow: Flow<Boolean>
    val getMeFlow: Flow<GetMeDto>
    val firstInit: LiveData<Boolean>
    val productItemDto: LiveData<ProductDto?>
    val logoutFlow: Flow<Boolean?>
    val logoutError: Flow<String?>
    val categoryId: LiveData<Int?>
    val categories: Flow<PagingData<CategoryDto>>

    val hasLoadedCategories: LiveData<Boolean>
    val hasLoadedProducts: LiveData<Boolean>

    val streamFlow: Flow<StreamDetailedDto>
    val streamsFlow: Flow<PagingData<StreamDetailedDto>>

    val hasLoadedStreams: LiveData<Boolean>
    val products: Flow<PagingData<ProductDto>>
    val productFlow: LiveData<ProductDto?>

    val createStreamFlow: Flow<StreamDto?>

    suspend fun getMe()

    fun getStoreProducts(category: Int?)

    fun refresh()

    fun getProductById(id: Int)

    fun search(value: String?)

    fun logout(data: LogoutRequest)

    fun setDeviceToken(data: SetDeviceTokenRequest)

    fun gotError()

    fun setScroll(x: Int, y: Int)

    fun getStreamById(id: Int)

    fun getAllStreams()

    fun getCategories()

    fun selectProduct(it: ProductDto)

    fun createStream(data: CreateStreamRequest)

    fun gotCreateSuccess()

}
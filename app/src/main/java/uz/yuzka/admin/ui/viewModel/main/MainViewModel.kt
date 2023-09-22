package uz.yuzka.admin.ui.viewModel.main

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.admin.data.request.CreateStreamRequest
import uz.yuzka.admin.data.request.SetDeviceTokenRequest
import uz.yuzka.admin.data.response.CategoryDto
import uz.yuzka.admin.data.response.GetMeDto
import uz.yuzka.admin.data.response.ProductDto
import uz.yuzka.admin.data.response.RegionDto
import uz.yuzka.admin.data.response.StreamDetailedDto
import uz.yuzka.admin.data.response.StreamDto

interface MainViewModel {

    val errorFlow: Flow<String?>
    val progressFlow: Flow<Boolean>
    val regionsProgressFlow: Flow<Boolean>
    val getMeFlow: Flow<GetMeDto>
    val firstInit: LiveData<Boolean>
    val productItemDto: LiveData<ProductDto?>
    val logoutFlow: Flow<Boolean>
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
    val deleteStreamFlow: Flow<Boolean>
    val updateUserFlow: LiveData<Boolean>

    val regions: Flow<List<RegionDto>>
    val hasLoadedRegions: LiveData<Boolean>


    fun getMe()

    fun getStoreProducts(category: Int?)

    fun refresh()

    fun getProductById(id: Int)

    fun search(value: String?)

    fun logout()

    fun setDeviceToken(data: SetDeviceTokenRequest)

    fun gotError()

    fun setScroll(x: Int, y: Int)

    fun getStreamById(id: Int)

    fun generatePost(id: Int)

    fun getAllStreams()

    fun getCategories()

    fun selectProduct(it: ProductDto)

    fun createStream(data: CreateStreamRequest)

    fun gotCreateSuccess()

    fun getMeFromLocal()

    fun getLocations()

    fun updateUser(
        name: String,
        surname: String,
        regionId: Int,
        districtId: Int,
        address: String
    )

    fun gotUpdateSuccess()

    fun deleteStream(id: Int)

    fun gotDeleteStream()

    fun updateStreams()

    fun gotLogout()
}
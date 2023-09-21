package uz.yuzka.admin.ui.viewModel.auth

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.yuzka.seller.data.request.LoginRequest
import uz.yuzka.seller.data.request.PasswordRequest
import uz.yuzka.admin.data.request.SetDeviceTokenRequest

interface AuthViewModel {

    val errorFlow: Flow<String?>
    val progressFlow: Flow<Boolean>
    val successFlow: Flow<Boolean>
    val startScreenFlow: Flow<Boolean>
    val introStartFlow: Flow<Boolean>
    val logoutFlow: Flow<Unit>
    val gotStores: Flow<Boolean?>
    val storeProgressFlow: Flow<Boolean>
    val phone: LiveData<String>

    val time: Flow<String>

    fun login(request: LoginRequest)

    fun getPassword(request: PasswordRequest)

    fun startScreen()

    fun setStartScreen(start: Boolean)

    fun introStart(boolean: Boolean)

    fun getIntroStart()

    fun logout()

    fun gotError()

    fun gotSuccess()

    fun setDeviceToken(data: SetDeviceTokenRequest)

    fun setPhone(phone: String)

    fun startTimer()

}

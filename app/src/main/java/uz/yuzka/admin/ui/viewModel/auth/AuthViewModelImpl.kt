package uz.yuzka.admin.ui.viewModel.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.yuzka.admin.data.request.LoginRequest
import uz.yuzka.admin.data.request.PasswordRequest
import uz.yuzka.admin.data.request.SetDeviceTokenRequest
import uz.yuzka.admin.data.request.UsernameLoginRequest
import uz.yuzka.admin.usecase.auth.AuthUseCase
import uz.yuzka.admin.usecase.main.MainUseCase
import uz.yuzka.admin.utils.eventFlow
import uz.yuzka.admin.utils.eventValueFlow
import uz.yuzka.admin.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class AuthViewModelImpl @Inject constructor(
    private val useCase: AuthUseCase,
    private val mainUseCase: MainUseCase
) : ViewModel(),
    AuthViewModel {
    override val errorFlow = eventValueFlow<String?>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val successFlow = eventValueFlow<Boolean>()
    override val startScreenFlow = eventValueFlow<Boolean>()
    override val introStartFlow = eventValueFlow<Boolean>()
    override val logoutFlow = eventFlow()
    override val gotStores = eventValueFlow<Boolean?>()
    override val storeProgressFlow = eventValueFlow<Boolean>()
    override val phone = MutableLiveData<String>()

    override val time = eventValueFlow<String>()

    private var timeJob: Job? = null

    override fun login(request: LoginRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo bo'ldi")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.login(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                introStartFlow.emit(true)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun loginByUsername(request: UsernameLoginRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo bo'ldi")
            }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        useCase.loginByUsername(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                introStartFlow.emit(true)
            }
            it.onFailure { throwable ->
                progressFlow.emit(false)
                errorFlow.emit(throwable.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun getPassword(request: PasswordRequest) {
        if (!isConnected()) {
            viewModelScope.launch {
                errorFlow.emit("Internet bilan muammo bo'ldi")
            }
            return
        }
        viewModelScope.launch { progressFlow.emit(true) }
        useCase.getPassword(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlow.emit(true)
            }
            it.onFailure { throwable ->
                errorFlow.emit(throwable.message.toString())
                progressFlow.emit(false)
            }
        }.launchIn(viewModelScope)
    }

    override fun startScreen() {
        viewModelScope.launch {
            startScreenFlow.emit(useCase.startScreen())
        }
    }

    override fun setStartScreen(start: Boolean) {
        viewModelScope.launch {
            useCase.setStartScreen(start)
        }
    }

    override fun introStart(boolean: Boolean) {
        useCase.introStart(boolean)
    }

    override fun getIntroStart() {
        viewModelScope.launch {
            introStartFlow.emit(useCase.getIntroStart())
        }
    }

    override fun logout() {
        viewModelScope.launch {
            logoutFlow.emit(useCase.logout())
        }
    }

    override fun gotError() {
        viewModelScope.launch {
            errorFlow.emit(null)
        }
    }

    override fun gotSuccess() {
        viewModelScope.launch {
            successFlow.emit(false)
        }
    }

    override fun setDeviceToken(data: SetDeviceTokenRequest) {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            mainUseCase.setDeviceToken(data).onEach {
                it.onSuccess {

                }
                it.onFailure {

                }
            }.launchIn(viewModelScope)
        }
    }

    override fun setPhone(phone: String) {
        viewModelScope.launch {
            this@AuthViewModelImpl.phone.value = phone
        }
    }

    override fun startTimer() {
        timeJob?.cancel()
        timeJob = viewModelScope.launch {
            var timer = 60
            while (timer > 0) {
                time.emit("0:${--timer}")
                delay(1000)
            }
        }
    }

}

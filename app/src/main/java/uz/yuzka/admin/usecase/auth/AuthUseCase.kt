package uz.yuzka.admin.usecase.auth

import kotlinx.coroutines.flow.Flow
import uz.yuzka.admin.data.request.LoginRequest
import uz.yuzka.admin.data.request.PasswordRequest
import uz.yuzka.admin.data.request.PasswordResponse
import uz.yuzka.admin.data.request.UsernameLoginRequest
import uz.yuzka.admin.data.response.LoginDto

interface AuthUseCase {

    fun login(body: LoginRequest): Flow<Result<LoginDto>>

    fun loginByUsername(body: UsernameLoginRequest): Flow<Result<LoginDto>>

    fun startScreen(): Boolean

    fun setStartScreen(start: Boolean)

    fun introStart(boolean: Boolean)

    fun getIntroStart(): Boolean

    fun logout()

    fun getPassword(body: PasswordRequest): Flow<Result<PasswordResponse>>
}
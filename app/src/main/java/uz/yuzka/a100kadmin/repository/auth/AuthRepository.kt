package uz.yuzka.a100kadmin.repository.auth

import kotlinx.coroutines.flow.Flow
import uz.yuzka.seller.data.request.LoginRequest
import uz.yuzka.seller.data.request.PasswordRequest
import uz.yuzka.seller.data.request.PasswordResponse
import uz.yuzka.a100kadmin.data.response.LoginDto

interface AuthRepository {

    fun login(body: LoginRequest): Flow<Result<LoginDto>>

    fun startScreen(): Boolean

    fun setStartScreen(startBool: Boolean)

    fun introScreen(startBool: Boolean)

    fun getIntroStart(): Boolean

    fun logout()
    fun getPassword(body: PasswordRequest): Flow<Result<PasswordResponse>>
}
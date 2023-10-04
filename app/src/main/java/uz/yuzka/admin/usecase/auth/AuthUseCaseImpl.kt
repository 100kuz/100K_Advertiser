package uz.yuzka.admin.usecase.auth

import kotlinx.coroutines.flow.Flow
import uz.yuzka.admin.data.request.LoginRequest
import uz.yuzka.admin.data.request.PasswordRequest
import uz.yuzka.admin.data.request.PasswordResponse
import uz.yuzka.admin.data.request.UsernameLoginRequest
import uz.yuzka.admin.data.response.LoginDto
import uz.yuzka.admin.repository.auth.AuthRepository
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(private val repository: AuthRepository) : AuthUseCase {

    override fun login(body: LoginRequest): Flow<Result<LoginDto>> = repository.login(body)

    override fun loginByUsername(body: UsernameLoginRequest): Flow<Result<LoginDto>> =
        repository.loginByUsername(body)

    override fun startScreen(): Boolean = repository.startScreen()
    override fun setStartScreen(start: Boolean) = repository.setStartScreen(start)

    override fun introStart(boolean: Boolean) {
        repository.introScreen(boolean)
    }

    override fun getIntroStart(): Boolean = repository.getIntroStart()
    override fun logout() {
        repository.logout()
    }

    override fun getPassword(body: PasswordRequest): Flow<Result<PasswordResponse>> =
        repository.getPassword(body)


}
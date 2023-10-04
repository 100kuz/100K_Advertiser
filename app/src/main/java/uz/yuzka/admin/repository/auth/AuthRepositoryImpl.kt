package uz.yuzka.admin.repository.auth

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.yuzka.admin.base.BaseErrorResponse
import uz.yuzka.admin.data.request.LoginRequest
import uz.yuzka.admin.data.request.PasswordRequest
import uz.yuzka.admin.data.request.PasswordResponse
import uz.yuzka.admin.data.request.UsernameLoginRequest
import uz.yuzka.admin.data.response.LoginDto
import uz.yuzka.admin.network.LoginApi
import uz.yuzka.admin.network.MainApi
import uz.yuzka.admin.pref.MyPref
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: LoginApi,
    private val pref: MyPref,
    private val mainApi: MainApi
) : AuthRepository {

    override fun login(body: LoginRequest): Flow<Result<LoginDto>> = flow {
        val response = api.login(body)
        if (response.isSuccessful) {
            val data = response.body()
            pref.token = data?.token
            pref.startScreen = !pref.token.isNullOrEmpty()
            pref.introScreen = true

            val getMe = mainApi.getMe()

            getMe.body()?.let {
                pref.getMeDto = it
            }

            emit(Result.success(data!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                emit(Result.failure(Throwable("Server bilan muammo yuzaga keldi!")))
                return@flow
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable("Server bilan muammo yuzaga keldi!")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)

    override fun loginByUsername(body: UsernameLoginRequest): Flow<Result<LoginDto>> = flow {
        val response = api.loginByUsername(body)
        if (response.isSuccessful) {
            val data = response.body()
            pref.token = data?.token
            pref.startScreen = !pref.token.isNullOrEmpty()
            pref.introScreen = true

            val getMe = mainApi.getMe()

            getMe.body()?.let {
                pref.getMeDto = it
            }

            emit(Result.success(data!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                emit(Result.failure(Throwable("Server bilan muammo yuzaga keldi!")))
                return@flow
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable("Server bilan muammo yuzaga keldi!")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)

    override fun getPassword(body: PasswordRequest): Flow<Result<PasswordResponse>> = flow {
        val response = api.getPassword(body)
        if (response.isSuccessful) {
            val data = response.body()
            emit(Result.success(data!!))
        } else {
            val errorData: BaseErrorResponse? = try {
                Gson().fromJson<BaseErrorResponse?>(
                    response.errorBody()?.string(),
                    object : TypeToken<BaseErrorResponse>() {}.type
                )
            } catch (e: Exception) {
                emit(Result.failure(Throwable("Server bilan muammo yuzaga keldi!")))
                return@flow
            }
            emit(Result.failure(Throwable(errorData?.message)))
        }
    }.catch {
        val errorMessage = Throwable("Server bilan muammo yuzaga keldi!")
        emit(Result.failure(errorMessage))
    }.flowOn(Dispatchers.IO)

    override fun startScreen(): Boolean = pref.startScreen

    override fun setStartScreen(startBool: Boolean) {
        pref.startScreen = startBool
    }

    override fun introScreen(startBool: Boolean) {
        pref.introScreen = startBool
    }

    override fun getIntroStart(): Boolean = pref.introScreen

    override fun logout() {
        pref.token = null
        pref.startScreen = false
        pref.getMeDto = null
    }

}
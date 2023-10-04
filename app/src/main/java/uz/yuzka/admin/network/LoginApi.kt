package uz.yuzka.admin.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.yuzka.admin.data.request.LoginRequest
import uz.yuzka.admin.data.request.PasswordRequest
import uz.yuzka.admin.data.request.PasswordResponse
import uz.yuzka.admin.data.request.UsernameLoginRequest
import uz.yuzka.admin.data.response.LoginDto

interface LoginApi {

    @POST("auth/verify-code")
    suspend fun login(@Body body: LoginRequest): Response<LoginDto>

    @POST("auth/sign-in")
    suspend fun loginByUsername(@Body body: UsernameLoginRequest): Response<LoginDto>

    @POST("auth/sms-login")
    suspend fun getPassword(@Body body: PasswordRequest): Response<PasswordResponse>

}
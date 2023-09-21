package uz.yuzka.admin.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.yuzka.admin.network.LoginApi
import uz.yuzka.admin.network.MainApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @[Provides Singleton]
    fun provideLoginApi(
        retrofit: Retrofit
    ): LoginApi = retrofit.create(LoginApi::class.java)

    @[Provides Singleton]
    fun provideMainApi(
        retrofit: Retrofit
    ): MainApi = retrofit.create(MainApi::class.java)
}
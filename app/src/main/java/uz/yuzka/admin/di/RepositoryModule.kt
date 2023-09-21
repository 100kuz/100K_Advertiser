package uz.yuzka.admin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.yuzka.admin.repository.auth.AuthRepository
import uz.yuzka.admin.repository.auth.AuthRepositoryImpl
import uz.yuzka.admin.repository.main.MainRepository
import uz.yuzka.admin.repository.main.MainRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun getMainRepository(impl: MainRepositoryImpl): MainRepository
}
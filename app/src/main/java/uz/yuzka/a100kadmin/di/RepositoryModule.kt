package uz.yuzka.a100kadmin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.yuzka.a100kadmin.repository.auth.AuthRepository
import uz.yuzka.a100kadmin.repository.auth.AuthRepositoryImpl
import uz.yuzka.a100kadmin.repository.main.MainRepository
import uz.yuzka.a100kadmin.repository.main.MainRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun getMainRepository(impl: MainRepositoryImpl): MainRepository
}
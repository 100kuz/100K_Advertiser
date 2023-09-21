package uz.yuzka.admin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.yuzka.admin.usecase.auth.AuthUseCase
import uz.yuzka.admin.usecase.auth.AuthUseCaseImpl
import uz.yuzka.admin.usecase.main.MainUseCase
import uz.yuzka.admin.usecase.main.MainUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface AuthUseCaseModule {

    @Binds
    fun getAuthUseCase(impl: AuthUseCaseImpl): AuthUseCase

    @Binds
    fun getMainUseCase(impl: MainUseCaseImpl): MainUseCase

}
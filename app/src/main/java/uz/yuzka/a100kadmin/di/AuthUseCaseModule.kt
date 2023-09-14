package uz.yuzka.a100kadmin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.yuzka.a100kadmin.usecase.auth.AuthUseCase
import uz.yuzka.a100kadmin.usecase.auth.AuthUseCaseImpl
import uz.yuzka.a100kadmin.usecase.main.MainUseCase
import uz.yuzka.a100kadmin.usecase.main.MainUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface AuthUseCaseModule {

    @Binds
    fun getAuthUseCase(impl: AuthUseCaseImpl): AuthUseCase

    @Binds
    fun getMainUseCase(impl: MainUseCaseImpl): MainUseCase

}
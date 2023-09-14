package uz.yuzka.a100kadmin.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.yuzka.a100kadmin.pref.MyPref
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @[Provides Singleton]
    fun getSharedPreferences(@ApplicationContext context: Context): MyPref = MyPref(context)

}
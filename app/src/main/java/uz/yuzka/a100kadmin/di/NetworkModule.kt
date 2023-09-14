package uz.yuzka.a100kadmin.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.yuzka.a100kadmin.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideChuckInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor = ChuckerInterceptor.Builder(context).build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor {
//        Timber.tag("NETWORK").d(it)
    }.apply {
        setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()


    @Provides
    @Singleton
    fun provideClient(
        appInterceptor: AppInterceptor,
        chuckInterceptor: ChuckerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)

        if (!builder.interceptors().contains(appInterceptor)) {
            builder.addInterceptor(appInterceptor)
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(chuckInterceptor)
            builder.addInterceptor(httpLoggingInterceptor)
        }

        builder.hostnameVerifier { _, _ -> true }

        return builder.build()
    }


}
package uz.yuzka.admin.di

import com.google.gson.GsonBuilder
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import uz.yuzka.admin.BuildConfig
import uz.yuzka.admin.pref.MyPref
import uz.yuzka.admin.data.response.LoginDto
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInterceptor @Inject constructor(
    private val cache: MyPref
) : Interceptor {

    private lateinit var response: Response

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = cache.token

        val original: Request = chain.request()

        response = if (token == null) chain.proceed(
            original.newBuilder().header("Content-Lang", Locale.getDefault().language)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json").build()
        )
        else chain.proceed(buildRequestWithToken(original, cache = cache))

        if (response.code == 401) {

            if (token != null) {

                val newRequest = Request.Builder()
                    .url("${BuildConfig.BASE_URL}auth/refresh-token?token=$token")
                    .post(FormBody.Builder().build())
                    .build()

                response.close()
                val newResponse = chain.proceed(newRequest)

                if (newResponse.code == 200) {

                    val responseBody = GsonBuilder().create()
                        .fromJson(newResponse.body?.string(), LoginDto::class.java)
                    cache.token = responseBody.token
                    return chain.proceed(
                        buildRequestWithToken(
                            original,
                            cache = cache
                        )
                    )
                } else {
                    cache.token = null
                }
            }
        }

        return response
    }
}

private fun buildRequestWithToken(
    request: Request,
    tokenType: String = "Bearer",
    cache: MyPref
): Request {

    val headers =
        Headers.Builder().add("Authorization", "$tokenType ${cache.token}").addUnsafeNonAscii(
            "Content-Lang",
            if (cache.language == "fr") "уз".trim() else cache.language
                ?: Locale.getDefault().language
        ).add("Content-Type", "application/json").add("Accept", "application/json").build()

    return request.newBuilder().headers(headers).build()
}


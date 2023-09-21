package uz.yuzka.admin.pref

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.yuzka.admin.utils.user
import uz.yuzka.admin.data.response.GetMeDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPref @Inject constructor(
    @ApplicationContext context: Context
) : Pref {

    private val pref = context.getSharedPreferences("cache", Context.MODE_PRIVATE)

    var token: String?
        set(value) {
            pref.edit().putString("token", value).apply()
        }
        get() = pref.getString("token", "")

    var fcmToken: String?
        set(value) {
            pref.edit().putString("fcm_token", value).apply()
        }
        get() = pref.getString("fcm_token", null)

    var lastFcmToken: String?
        set(value) {
            pref.edit().putString("last_fcm_token", value).apply()
        }
        get() = pref.getString("last_fcm_token", null)

    var language: String?
        set(value) {
            pref.edit().putString("language", value).apply()
        }
        get() = pref.getString("language", "")!!

    var startScreen: Boolean
        set(value) {
            pref.edit().putBoolean("startScreen", value).apply()
        }
        get() = pref.getBoolean("startScreen", false)

    var introScreen: Boolean
        set(value) {
            pref.edit().putBoolean("introScreen", value).apply()
        }
        get() = pref.getBoolean("introScreen", false)

    var lang: String
        set(value) {
            pref.edit().putString("lang", value).apply()
        }
        get() = pref.getString("lang", "uz") ?: "uz"

    var selectedStore: Boolean
        set(value) {
            pref.edit().putBoolean("selectStore", value).apply()
        }
        get() = pref.getBoolean("selectStore", false)

    override var getMeDto: GetMeDto? by pref.user()

    var currentStore: Int
        set(value) {
            pref.edit().putInt("currentStore", value).apply()
        }
        get() = pref.getInt("currentStore", -1)

}
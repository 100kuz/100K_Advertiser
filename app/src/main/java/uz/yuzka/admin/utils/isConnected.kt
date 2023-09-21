package uz.yuzka.admin.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.yuzka.admin.app.App
import uz.yuzka.admin.data.response.GetMeDto
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun isConnected(): Boolean = App.instance.isAvailableNetwork()

private fun Context.isAvailableNetwork(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun String.formatToPrice(): String {
    val value = replace(" ", "")
    val reverseValue = StringBuilder(value).reverse()
        .toString()
    val finalValue = StringBuilder()
    for (i in 1..reverseValue.length) {
        val `val` = reverseValue[i - 1]
        finalValue.append(`val`)
        if (i % 3 == 0 && i != reverseValue.length && i > 0) {
            finalValue.append(" ")
        }
    }
    return finalValue.reverse().toString()
}

fun SharedPreferences.user(
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, GetMeDto?> = object : ReadWriteProperty<Any, GetMeDto?> {

    override fun setValue(thisRef: Any, property: KProperty<*>, value: GetMeDto?): Unit =
        edit().putString(key(property), Gson().toJson(value)).apply()


    override fun getValue(thisRef: Any, property: KProperty<*>): GetMeDto? {
        val json = getString(key(property), null)
        if (json.isNullOrEmpty()) return null
        return Gson().fromJson(json, object : TypeToken<GetMeDto>() {}.type)
    }

}

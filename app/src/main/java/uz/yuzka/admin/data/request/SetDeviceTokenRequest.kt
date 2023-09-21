package uz.yuzka.admin.data.request

data class SetDeviceTokenRequest(
    val app_id: String = "uz.yuzka.advertiser",
    val device_type: String = "android",
    val device_id: String
)
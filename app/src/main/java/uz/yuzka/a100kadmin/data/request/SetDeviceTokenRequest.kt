package uz.yuzka.seller.data.request

data class SetDeviceTokenRequest(
    val app_id: String = "uz.yuzka.seller",
    val device_type: String = "android",
    val device_id: String
)
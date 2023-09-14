package uz.yuzka.a100kadmin.data

import java.io.File

data class PositionItem(
    var id: Int,
    var price: String,
    var quantity: String,
    var name: String = "",
    var image: File? = null,
    var percent: Int = 25,
    var hasSku: Boolean = true,
    var skuCode: String? = null,
)

package uz.yuzka.a100kadmin.ui.viewModel.createpromocode

import kotlinx.coroutines.flow.Flow
import uz.yuzka.a100kadmin.data.response.PromoCodeItem

interface CreatePromoCodeViewModel {

    val progressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val hasCreatedPromoCode: Flow<PromoCodeItem?>

    fun createPromoCode(name: String)

    fun gotError()

    fun clearData()

    fun setData(promoCodeData: PromoCodeItem)

}
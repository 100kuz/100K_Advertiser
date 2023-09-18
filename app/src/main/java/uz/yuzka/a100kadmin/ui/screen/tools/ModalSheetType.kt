package uz.yuzka.a100kadmin.ui.screen.tools

sealed class ModalSheetType(val type: String) {
    object SetDiscount : ModalSheetType("discount")
    object SetCharity : ModalSheetType("charity")
}

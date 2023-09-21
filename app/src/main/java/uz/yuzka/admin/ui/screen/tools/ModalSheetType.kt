package uz.yuzka.admin.ui.screen.tools

sealed class ModalSheetType {
    object SetDiscount : ModalSheetType()
    object SetCharity : ModalSheetType()
    object Regions : ModalSheetType()
    object District : ModalSheetType()
}

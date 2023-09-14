package uz.yuzka.a100kadmin.base

sealed class Status(val id: Int, val status: String?) {
    object ALL : Status(0, null)
    object ACTIVE : Status(1, "active")
    object INACTIVE : Status(2, "inactive")
    object BLOCKED : Status(3, "blocked")
    object NEW : Status(4, "new")
}

sealed class SaleStatus(val id: Int, val status: String?, val label: String) {
    object ALL : SaleStatus(0, null, "Barchasi")
    object NEW : SaleStatus(1, "new", "Yangi")
    object ACCEPTED : SaleStatus(1, "accepted", "Yetqazishga tayyor")
    object SENT : SaleStatus(1, "sent", "Yo'lda")
    object HOLD : SaleStatus(1, "hold", "Hold")
    object DELIVERED : SaleStatus(2, "completed", "Yetqazildi")
    object CANCELLED : SaleStatus(3, "canceled", "Bekor qilindi")
    object ARCHIVE : SaleStatus(3, "archived", "Arxiv")
}

sealed class ReviewStatus(val id: Int, val status: String?) {
    object ALL : ReviewStatus(0, null)
    object UNREAD : ReviewStatus(1, "unread")
    object NOTANSWERED : ReviewStatus(2, "not_answered")
    object ANSWERED : ReviewStatus(3, "answered")
}
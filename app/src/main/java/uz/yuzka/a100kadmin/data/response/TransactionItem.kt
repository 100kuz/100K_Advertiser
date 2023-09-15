package uz.yuzka.a100kadmin.data.response

import uz.yuzka.a100kadmin.base.Response

typealias TransactionsResponse = Response<List<TransactionItem>>

data class TransactionItem(
    val amount: Int,
    val code: String?,
    val comment: String,
    val created_at: String,
    val created_at_label: String,
    val id: Int,
    val target_id: Int?,
    val target_type: String?,
    val transactionable_id: Int,
    val transactionable_type: String,
    val type: String
)
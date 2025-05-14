package dev.stashy.kongo

import com.mongodb.TransactionOptions
import com.mongodb.kotlin.client.coroutine.ClientSession

suspend inline fun <T> ClientSession.transaction(
    options: TransactionOptions.Builder.() -> Unit = {},
    fn: suspend (session: ClientSession) -> T
) {
    startTransaction(TransactionOptions.builder().apply(options).build())
    try {
        val value = fn.invoke(this)
        commitTransaction()
    } catch (e: Exception) {
        abortTransaction()
        throw e
    }
}

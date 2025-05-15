package dev.stashy.kongo

import com.mongodb.TransactionOptions
import com.mongodb.kotlin.client.coroutine.ClientSession

/**
 * Automatically starts a transaction, commits if no exceptions are thrown and aborts the transaction otherwise.
 *
 * @see [ClientSession.startTransaction]
 * @see [ClientSession.commitTransaction]
 * @see [ClientSession.abortTransaction]
 */
suspend inline fun <T> ClientSession.transaction(
    options: TransactionOptions.Builder.() -> Unit = {},
    fn: suspend (session: ClientSession) -> T
): Result<T> {
    startTransaction(TransactionOptions.builder().apply(options).build())
    try {
        val value = fn.invoke(this)
        commitTransaction()
        return Result.success(value)
    } catch (e: Exception) {
        abortTransaction()
        return Result.failure(e)
    }
}

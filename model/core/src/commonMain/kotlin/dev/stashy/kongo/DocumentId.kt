package dev.stashy.kongo

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * A replacement for MongoDB's `ObjectId` class that does not depend on any MongoDB libraries.
 *
 * Transforms into a simple string.
 * To use `DocumentId` in a module that uses MongoDB, use the `model-bson` module.
 */
@JvmInline
@Serializable(with = DocumentIdSerializer::class)
value class DocumentId(val value: String) : Comparable<DocumentId> {
    /**
     * Builds a new DocumentId from its components.
     */
    constructor(timestamp: Long, machineId: Long, processId: Long, counter: Long) : this(buildString {
        append(timestamp.toString(16).padStart(8, '0'))
        append((machineId and 0xFFFFFF).toString(16).padStart(6, '0'))
        append((processId and 0xFFFF).toString(16).padStart(4, '0'))
        append((counter and 0xFFFFFF).toString(16).padStart(6, '0'))
    })

    companion object {
        /**
         * This special DocumentId is serialized as null, causing a new ID to be generated if used in MongoDB.
         * It is not a valid ID in the first place, so if anything goes wrong, the operation will simply fail.
         */
        val None: DocumentId = DocumentId("none")
    }

    val timestamp: Long get() = value.take(8).toLongOrNull(radix = 16) ?: 0L
    val machineId: Long get() = value.substring(8, 14).toLongOrNull(radix = 16) ?: 0L
    val processId: Long get() = value.substring(14, 18).toLongOrNull(radix = 16) ?: 0L
    val counter: Long get() = value.substring(18, 24).toLongOrNull(radix = 16) ?: 0L

    override fun compareTo(other: DocumentId): Int {
        if (this == None && other == None) return 0
        if (this == None) return -1
        if (other == None) return 1

        val thisTime = this.timestamp
        val otherTime = other.timestamp

        val timeComparison = thisTime.compareTo(otherTime)
        if (timeComparison != 0) return timeComparison

        val thisRemainder = this.value.substring(8)
        val otherRemainder = other.value.substring(8)

        return thisRemainder.compareTo(otherRemainder)
    }
}

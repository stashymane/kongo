package dev.stashy.kongo

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * A replacement for MongoDB's `ObjectId` class that does not depend on any MongoDB libraries.
 *
 * Transforms into a simple string.
 * To use `DocumentId` in a module that uses MongoDB, use the `:services` module instead.
 */
@JvmInline
@Serializable(with = DocumentIdSerializer::class)
value class DocumentId(val value: String) {
    companion object {
        /**
         * This special DocumentId is serialized as null, causing a new ID to be generated if used in MongoDB.
         * It is not a valid ID in the first place, so if anything goes wrong, the operation will simply fail.
         */
        val None: DocumentId = DocumentId("none")
    }
}

package dev.stashy.kongo

import kotlinx.serialization.Serializable
import org.bson.BsonObjectId
import org.bson.types.ObjectId

/**
 * A replacement for MongoDB's `ObjectId` class that does not depend on any MongoDB libraries.
 *
 * Transforms into an [ObjectId] when used in MongoDB.
 * To use `DocumentId` in non-JVM projects, use the `:model` module instead.
 */
@JvmInline
@Serializable(with = DocumentIdBsonSerializer::class)
value class DocumentId(val value: String)

fun ObjectId.toDocumentId(): DocumentId = DocumentId(this.toHexString())
fun BsonObjectId.toDocumentId(): DocumentId = DocumentId(this.value.toHexString())
fun DocumentId.toObjectId(): ObjectId = ObjectId(value)

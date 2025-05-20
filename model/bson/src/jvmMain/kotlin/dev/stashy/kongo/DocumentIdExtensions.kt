package dev.stashy.kongo

import org.bson.BsonObjectId
import org.bson.types.ObjectId

fun DocumentId.toObjectId(): ObjectId = ObjectId(value)
fun ObjectId.toDocumentId(): DocumentId = DocumentId(this.toHexString())
fun BsonObjectId.toDocumentId(): DocumentId = DocumentId(this.value.toHexString())

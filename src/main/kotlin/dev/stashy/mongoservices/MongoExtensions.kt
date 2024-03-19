package dev.stashy.mongoservices

import org.bson.BsonValue

/**
 * Convert an ObjectId value to a string.
 * @return String representation of the ObjectId value, or null if this is not an ObjectId.
 */
fun BsonValue.toObjectIdStringOrNull(): String? = if (isObjectId) asObjectId().value.toHexString() else null

/**
 * Convert an ObjectId value to a string.
 * @return String representation of the ObjectId value.
 * @throws BsonInvalidOperationException if this BsonValue is not an ObjectId.
 */
fun BsonValue.toObjectIdString(): String? = asObjectId().value.toHexString()

package dev.stashy.kongo

import com.mongodb.client.result.InsertManyResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.bson.BsonObjectId
import org.bson.BsonValue

/**
 * @see [InsertOneResult.getInsertedId]
 */
val InsertOneResult.documentId: DocumentId? get() = insertedId?.asObjectId()?.toDocumentId()

/**
 * @see [InsertManyResult.getInsertedIds]
 */
val InsertManyResult.documentIds: List<DocumentId>
    get() = insertedIds.values.map(BsonValue::asObjectId).map(BsonObjectId::toDocumentId)

/**
 * @see [UpdateResult.getUpsertedId]
 */
val UpdateResult.upsertedDocumentId: DocumentId? get() = upsertedId?.asObjectId()?.toDocumentId()

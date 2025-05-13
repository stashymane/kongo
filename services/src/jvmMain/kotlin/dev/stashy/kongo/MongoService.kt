package dev.stashy.kongo

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlin.reflect.KClass

/**
 * Class for defining a MongoDB-based storage service, with various utility functions to ease writing queries.
 *
 * Example:
 * ```
 * data class Foo(@SerialName("_id") @Contextual val id: DocumentId, val bar: String)
 *
 * class DataService(db: MongoDatabase): MongoService<Foo>("foo", db, Foo::class) {
 *     fun getById(id: DocumentId): Foo? = collection.find(...)
 *     ...
 * }
 * ```
 *
 * @see MongoServiceBase
 */
abstract class MongoService<T : Any>(
    val collectionName: String,
    val database: MongoDatabase,
    type: KClass<T>
) : MongoServiceBase<T> {
    /**
     * Creates the collection if it does not yet exist, and performs other database setup based on your implementation.
     */
    override suspend fun init() {
        database.createCollection(collectionName)
    }

    /**
     * The MongoDB collection associated with this service.
     */
    override val collection = database.getCollection(collectionName, type.java)
}

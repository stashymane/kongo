package dev.stashy.mongoservices

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.stashy.mongoservices.builders.FilterBuilder
import dev.stashy.mongoservices.builders.UpdateBuilder
import org.bson.conversions.Bson
import kotlin.reflect.KClass

/**
 * Class for defining a MongoDB-based storage service, with various utility functions to ease in writing queries.
 *
 * Example:
 * ```
 * data class Foo(@SerialName("_id") @Contextual val id: ObjectId, val bar: String)
 *
 * class DataService(db: MongoDatabase): MongoService<Foo>("foo", db, Foo::class) {
 *     fun getById(id: ObjectId): Foo? = collection.find(...)
 *     ...
 * }
 * ```
 */
abstract class MongoService<T : Any>(
    private val name: String,
    private val database: MongoDatabase,
    type: KClass<T>
) {
    /**
     * Creates the collection if it does not yet exist, and performs other database setup based on your implementation.
     */
    open suspend fun init() {
        database.createCollection(name)
    }

    /**
     * The MongoDB collection associated with this service.
     */
    val collection = database.getCollection(name, type.java)

    inline fun filter(fn: FilterBuilder<T>.() -> Bson): Bson = fn(FilterBuilder())
    inline fun update(fn: UpdateBuilder<T>.() -> Bson): Bson = fn(UpdateBuilder())
}

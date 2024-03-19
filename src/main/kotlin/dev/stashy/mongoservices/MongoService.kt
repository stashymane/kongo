package dev.stashy.mongoservices

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.conversions.Bson
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

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

    infix fun <V> KProperty1<T, V>.equals(value: V): Bson = Filters.eq(name, value)
    infix fun <V> KProperty1<T, V>.notEquals(value: V): Bson = Filters.ne(name, value)

    infix fun <V> KProperty1<T, V>.greaterThan(value: V & Any): Bson = Filters.gt(name, value)
    infix fun <V> KProperty1<T, V>.lessThan(value: V & Any): Bson = Filters.lt(name, value)

    infix fun <V> KProperty1<T, V>.greaterThanOrEquals(value: V & Any): Bson = Filters.gte(name, value)
    infix fun <V> KProperty1<T, V>.lessThanOrEquals(value: V & Any): Bson = Filters.lte(name, value)

    infix fun <V> KProperty1<T, V>.containedIn(value: V): Bson = Filters.`in`(name, value)
    infix fun <V> KProperty1<T, V>.notContainedIn(value: V): Bson = Filters.nin(name, value)

    infix fun <V> KProperty1<T, V>.all(value: V): Bson = Filters.all(name, value)

    infix fun Bson.and(value: Bson): Bson = Filters.and(this, value)
    infix fun Bson.or(value: Bson): Bson = Filters.or(this, value)
    infix fun Bson.nor(value: Bson): Bson = Filters.nor(this, value)
}

package dev.stashy.kongo.service

import com.mongodb.client.model.CreateCollectionOptions
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.stashy.kongo.builders.*
import org.bson.conversions.Bson
import kotlin.properties.ReadOnlyProperty

/**
 * A service for abstracting MongoDB internals.
 *
 * Example:
 * ```kotlin
 * data class Foo(
 *     @SerialName("_id") @Contextual val id: DocumentId,
 *     val bar: String
 * )
 *
 * class FooService : KongoService<Foo> {
 *     override val info by meta(name = "test")
 *     override val database by inject() // provide instance however you want
 *
 *     suspend fun getFoo(bar: String): Foo {
 *         return collection.find { Foo::bar equals bar }.first()
 *     }
 * }
 * ```
 *
 * @param T The type of object stored in the collection.
 * @property info Metadata for the service. See [meta].
 * @property database The database to use in this service.
 * @property collection The collection to use in this service.
 */
interface KongoService<T : Any> {
    val info: Info<T>
    val database: MongoDatabase

    val collection: MongoCollection<T>
        get() = database.getCollection(info.name, info.type)

    /**
     * Information describing your Kongo service.
     * Use the [meta] delegate for less verbosity.
     *
     * @property name The name of the collection.
     * @property type The type of object stored in the collection.
     * @property options Options to use when creating the collection.
     */
    data class Info<T : Any>(
        val name: String,
        val type: Class<T>,
        val options: CreateCollectionOptions
    )
}

/**
 * @param T The type of object stored in the collection.
 * @param name The name of the collection.
 * @param options Options to use when creating the collection.
 *
 * @return A delegate for the [KongoService.Info] object describing your service.
 */
inline fun <reified T : Any> KongoService<T>.meta(
    name: String,
    options: CreateCollectionOptions = CreateCollectionOptions()
): ReadOnlyProperty<Any?, KongoService.Info<T>> = ReadOnlyProperty { thisRef, property ->
    KongoService.Info(name, T::class.java, options)
}

/**
 * Builder for sorts.
 * @see com.mongodb.client.model.Sorts
 */
inline fun <T : Any> KongoService<T>.sort(fn: SortBuilder<T>.() -> Bson): Bson = fn(SortBuilder())

/**
 * Builder for indexes.
 * @see com.mongodb.client.model.Indexes
 */
inline fun <T : Any> KongoService<T>.index(fn: IndexBuilder<T>.() -> Bson): Bson = fn(IndexBuilder())

/**
 * Builder for updates.
 * @see com.mongodb.client.model.Updates
 */
inline fun <T : Any> KongoService<T>.update(fn: UpdateBuilder<T>.() -> Bson): Bson = fn(UpdateBuilder())

/**
 * Builder for filters.
 * @see com.mongodb.client.model.Filters
 */
inline fun <T : Any> KongoService<T>.filter(fn: FilterBuilder<T>.() -> Bson): Bson = fn(FilterBuilder())

/**
 * Builder for projections.
 * @see com.mongodb.client.model.Projections
 */
inline fun <T : Any> KongoService<T>.projection(fn: ProjectionBuilder<T>.() -> Bson): Bson = fn(ProjectionBuilder())

package dev.stashy.kongo.service

import com.mongodb.client.model.CreateCollectionOptions
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
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
 */
interface KongoService<T : Any> {
    /**
     * Metadata for the service. See [meta].
     */
    val info: Info<T>

    /**
     * The database used with this service.
     */
    val database: MongoDatabase

    /**
     * The MongoDB collection associated with this service.
     * By default, gets the collection from the [database] property.
     */
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

    /**
     * Runs an [operation] on this [collection].
     *
     * Does not guarantee ACID. For that, see [dev.stashy.kongo.transaction].
     *
     * @return [Result.Failure] if an exception occurs, otherwise [Result].
     */
    suspend fun <R> operation(fn: suspend MongoCollection<T>.() -> R): Result<R> = runCatching {
        fn.invoke(collection)
    }
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

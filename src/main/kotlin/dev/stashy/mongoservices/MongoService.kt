package dev.stashy.mongoservices

import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries
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
) : MongoServiceBase<T> {
    /**
     * Creates the collection if it does not yet exist, and performs other database setup based on your implementation.
     */
    override suspend fun init() {
        database.createCollection(name)
    }

    /**
     * The MongoDB collection associated with this service.
     *
     * This installs the [EntityId] codec registry by default.
     * When overriding the codec registry, either extend the collection's current one, or just include the [EntityIdBsonSerializer.entityIdCodec].
     */
    override val collection = database.getCollection(name, type.java).withCodecRegistry(
        CodecRegistries.fromRegistries(
            CodecRegistries.fromCodecs(EntityIdBsonSerializer.entityIdCodec),
            MongoClientSettings.getDefaultCodecRegistry()
        )
    )
}

package dev.stashy.mongoservices

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.stashy.mongoservices.model.DocumentId
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import kotlinx.serialization.modules.contextual
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.kotlinx.BsonConfiguration
import org.bson.codecs.kotlinx.KotlinSerializerCodec
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

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

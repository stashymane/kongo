package dev.stashy.kongo.builders

import com.mongodb.MongoClientSettings
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.kotlinx.BsonConfiguration
import org.bson.codecs.kotlinx.KotlinSerializerCodec
import org.bson.codecs.kotlinx.KotlinSerializerCodecProvider
import org.bson.codecs.kotlinx.defaultSerializersModule
import kotlin.reflect.KClass

class RegistryBuilder {
    private val collection = mutableListOf<CodecProvider>()
    internal fun build(): CodecRegistry = CodecRegistries.fromProviders(collection)

    init {
        KotlinSerializerCodecProvider()
    }

    fun provider(vararg provider: CodecProvider) {
        collection.addAll(provider)
    }

    fun codec(vararg codec: Codec<*>) {
        collection += CodecRegistries.fromCodecs(*codec)
    }

    /**
     * Installs a custom [SerializersModule] for the generic Kotlin serialization provider.
     * Remember to include the [DocumentIdBsonSerializer].
     */
    @OptIn(ExperimentalSerializationApi::class)
    fun kotlinSerializationProvider(
        serializersModule: SerializersModule = defaultSerializersModule,
        bsonConfiguration: BsonConfiguration = BsonConfiguration()
    ) = provider(KotlinSerializerCodecProvider(serializersModule, bsonConfiguration))

    /**
     * Installs a Kotlin serialization codec for a single [KClass].
     */
    fun <T : Any> kotlinSerializationCodec(
        kClass: KClass<T>,
        serializer: KSerializer<T>,
        serializersModule: SerializersModule,
        bsonConfiguration: BsonConfiguration = BsonConfiguration()
    ) = codec(KotlinSerializerCodec.create(kClass, serializer, serializersModule, bsonConfiguration))

    /**
     * Installs a Kotlin serialization codec for a single [KClass].
     */
    inline fun <reified T : Any> kotlinSerializationCodec(
        serializersModule: SerializersModule,
        bsonConfiguration: BsonConfiguration = BsonConfiguration()
    ) = kotlinSerializationCodec(T::class, serializer<T>(), serializersModule, bsonConfiguration)

    /**
     * Installs the default codec registry as dictated by [MongoClientSettings.getDefaultCodecRegistry]
     */
    fun default() = provider(MongoClientSettings.getDefaultCodecRegistry())
}

fun codecRegistry(fn: RegistryBuilder.() -> Unit): CodecRegistry = RegistryBuilder().apply(fn).build()

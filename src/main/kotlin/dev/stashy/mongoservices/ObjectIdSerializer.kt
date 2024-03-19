package dev.stashy.mongoservices

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.types.ObjectId

/**
 * Serializer for ObjectIds for use outside the MongoDB driver.
 * This simply converts ObjectIds to regular strings and back.
 *
 * Since the ObjectId serializer is contextual, you need to include it in your `serializersModule` in every serializer, e.g.:
 * ```
 * val json = Json {
 *  serializersModule += SerializersModule { contextual(ObjectIdSerializer) }
 * }
 * ```
 */
object ObjectIdSerializer : KSerializer<ObjectId> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ObjectId) {
        val surrogate = value.toHexString()
        encoder.encodeSerializableValue(String.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): ObjectId {
        val surrogate = decoder.decodeSerializableValue(String.serializer())
        return ObjectId(surrogate)
    }
}

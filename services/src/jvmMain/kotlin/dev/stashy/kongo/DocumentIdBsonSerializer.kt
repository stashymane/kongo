package dev.stashy.kongo

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.codecs.kotlinx.BsonDecoder
import org.bson.codecs.kotlinx.BsonEncoder
import org.bson.types.ObjectId

/**
 * A simple string serializer for [DocumentId].
 * Used in projects that contain MongoDB libraries.
 */
object DocumentIdBsonSerializer : KSerializer<DocumentId> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DocumentIdBsonSerializer", PrimitiveKind.STRING)

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: DocumentId) {
        when (encoder) {
            is BsonEncoder -> encoder.encodeObjectId(ObjectId(value.value))
            else -> encoder.encodeString(value.value)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): DocumentId {
        return when (decoder) {
            is BsonDecoder -> decoder.decodeObjectId().toDocumentId()
            else -> DocumentId(decoder.decodeString())
        }
    }
}

package dev.stashy.kongo

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.codecs.kotlinx.BsonDecoder
import org.bson.codecs.kotlinx.BsonEncoder
import org.bson.types.ObjectId

class BsonDocumentIdSerializer : DocumentIdSerializerProvider {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.stashy.kongo.DocumentIdBsonSerializer", PrimitiveKind.STRING)

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


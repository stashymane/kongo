package dev.stashy.kongo

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A simple string serializer for [DocumentId].
 * Used in libraries that do not include MongoDB libraries.
 */
object DocumentIdSerializer : KSerializer<DocumentId> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("dev.stashy.kongo.DocumentIdSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DocumentId) {
        encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DocumentId =
        DocumentId(decoder.decodeString())
}

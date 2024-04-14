package dev.stashy.mongoservices.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A simple string serializer for [DocumentId].
 * Needs to be contextual to properly serialize `ObjectId`s in MongoDB's driver.
 */
object DocumentIdSerializer : KSerializer<DocumentId> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DocumentIdSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DocumentId) {
        encoder.encodeString(value.value)
    }

    override fun deserialize(decoder: Decoder): DocumentId =
        DocumentId(decoder.decodeString())
}

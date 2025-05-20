package dev.stashy.kongo

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * A simple string serializer for [DocumentId].
 * When used with MongoDB, automatically transforms to an ObjectId.
 */
expect object DocumentIdSerializer : KSerializer<DocumentId> {
    override val descriptor: SerialDescriptor

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: DocumentId)

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): DocumentId
}

internal val documentIdDescriptor =
    PrimitiveSerialDescriptor("dev.stashy.kongo.DocumentIdSerializer", PrimitiveKind.STRING)

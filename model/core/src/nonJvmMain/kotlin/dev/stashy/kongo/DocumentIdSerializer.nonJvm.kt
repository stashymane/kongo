package dev.stashy.kongo

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

actual object DocumentIdSerializer : KSerializer<DocumentId> {
    actual override val descriptor: SerialDescriptor = documentIdDescriptor

    @OptIn(markerClass = [ExperimentalSerializationApi::class])
    actual override fun serialize(encoder: Encoder, value: DocumentId) {
        encoder.encodeString(value.value)
    }

    @OptIn(markerClass = [ExperimentalSerializationApi::class])
    actual override fun deserialize(decoder: Decoder): DocumentId =
        DocumentId(decoder.decodeString())
}

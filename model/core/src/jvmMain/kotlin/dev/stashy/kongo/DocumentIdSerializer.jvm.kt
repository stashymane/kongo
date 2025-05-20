package dev.stashy.kongo

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

actual object DocumentIdSerializer : KSerializer<DocumentId> by loadSerializer()

object DefaultDocumentIdSerializer : KSerializer<DocumentId> {
    override val descriptor: SerialDescriptor = documentIdDescriptor

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: DocumentId) {
        encoder.encodeString(value.value)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): DocumentId =
        DocumentId(decoder.decodeString())
}

fun loadSerializer(): KSerializer<DocumentId> {
    val loader = ServiceLoader.load(DocumentIdSerializerProvider::class.java)
    return loader.firstOrNull() ?: DefaultDocumentIdSerializer
}

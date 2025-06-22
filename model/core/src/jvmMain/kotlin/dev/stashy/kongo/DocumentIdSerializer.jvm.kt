package dev.stashy.kongo

import kotlinx.serialization.KSerializer
import java.util.*

actual object DocumentIdSerializer : KSerializer<DocumentId> by loadSerializer()

fun loadSerializer(): KSerializer<DocumentId> {
    val loader = ServiceLoader.load(DocumentIdSerializerProvider::class.java)
    return loader.firstOrNull() ?: DefaultDocumentIdSerializer
}

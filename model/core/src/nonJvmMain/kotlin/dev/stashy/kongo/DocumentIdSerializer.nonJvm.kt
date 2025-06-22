package dev.stashy.kongo

import kotlinx.serialization.KSerializer

actual object DocumentIdSerializer : KSerializer<DocumentId> by DefaultDocumentIdSerializer

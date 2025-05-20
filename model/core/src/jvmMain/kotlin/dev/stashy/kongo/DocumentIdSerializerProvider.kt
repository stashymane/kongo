package dev.stashy.kongo

import kotlinx.serialization.KSerializer

interface DocumentIdSerializerProvider : KSerializer<DocumentId>

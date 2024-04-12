package dev.stashy.mongoservices.model

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class EntityId(val id: String)

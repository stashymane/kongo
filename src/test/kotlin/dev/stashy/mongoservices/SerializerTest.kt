package dev.stashy.mongoservices

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.plus
import org.bson.types.ObjectId
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializerTest {
    val json = Json {
        serializersModule += SerializersModule { contextual(ObjectIdSerializer) }
    }

    @Test
    fun testSerialization() {
        val id = ObjectId()

        val jsonValue = json.encodeToString(id)
        val decodedValue = json.decodeFromString<ObjectId>(jsonValue)

        assertEquals(id, decodedValue)
    }
}
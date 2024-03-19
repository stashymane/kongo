package dev.stashy.mongoservices

import org.bson.BsonObjectId
import org.bson.BsonValue
import org.bson.types.ObjectId
import kotlin.test.Test
import kotlin.test.assertEquals

class MongoExtensionsTest {
    @Test
    fun testObjectIdToString() {
        val idString = "65f9769c08f045207c20f739"
        val id: BsonValue = BsonObjectId(ObjectId(idString))

        assertEquals(idString, id.toObjectIdString(), "Converted BsonObjectId does not match the input string.")
    }
}
package dev.stashy.mongoservices

import com.mongodb.client.model.Filters
import dev.stashy.mongoservices.builders.FilterBuilder
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterBuilderTest {
    @Test
    fun rawUsage() {
        data class TestObject(val foo: String)

        val field = TestObject::foo
        val value = "bar"

        val original = Filters.eq(field.name, value)
        val new = FilterBuilder<TestObject>().run { field equals value }

        assertEquals(original, new, "Filter result should be the same.")
    }

    @Test
    fun testSerialNameFilter() {
        @Serializable
        data class TestObject(@SerialName("_id") val mongoId: @Contextual ObjectId)

        val id = ObjectId()
        val testObj = TestObject(id)

        val filter = FilterBuilder<TestObject>().run {
            TestObject::mongoId equals id
        }

        val fieldName = filter.toBsonDocument().firstKey
        assertEquals("_id", fieldName, "SerialName for ID field has not been read successfully.")
    }
}
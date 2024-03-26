package dev.stashy.mongoservices

import com.mongodb.client.model.Filters
import dev.stashy.mongoservices.builders.FilterBuilder
import dev.stashy.mongoservices.builders.UpdateBuilder
import dev.stashy.mongoservices.builders.fieldToValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterBuilderTest {
    @Serializable
    data class TestObject(@SerialName("_id") val mongoId: @Contextual ObjectId, val foo: String)

    @Test
    fun rawUsage() {

        val field = TestObject::foo
        val value = "bar"

        val original = Filters.eq(field.name, value)
        val new = FilterBuilder<TestObject>().run { field equals value }

        assertEquals(original, new, "Filter result should be the same.")
    }

    @Test
    fun testSerialNameFilter() {

        val id = ObjectId()
        val testObj = TestObject(id, "test")

        val filter = FilterBuilder<TestObject>().run {
            TestObject::mongoId equals id
        }

        val fieldName = filter.toBsonDocument().firstKey
        assertEquals("_id", fieldName, "SerialName for ID field has not been read successfully.")
    }

    @Test
    fun fieldToValueTest() {
        val value = "testing"
        val obj = TestObject(ObjectId(), value)
        val actual = obj.fieldToValue(TestObject::foo)
        assertEquals(TestObject::foo, actual.first, "Property has not been mapped correctly.")
        assertEquals(value, actual.second, "Property value has not been mapped correctly.")
    }

    @Test
    fun setFieldValuesTest() {
        val value = "testing"
        val obj = TestObject(ObjectId(), value)
        val document = UpdateBuilder<TestObject>().run {
            obj.setFields(listOf(TestObject::foo))
        }.toBsonDocument()

        assertEquals("\$set", document.firstKey)

        val setObject = document.getDocument("\$set")
        assertEquals(TestObject::foo.name, setObject.firstKey)
        assertEquals(value, setObject.getString(setObject.firstKey).value)
    }
}
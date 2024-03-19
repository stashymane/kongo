package dev.stashy.mongoservices

import com.mongodb.client.model.Filters
import dev.stashy.mongoservices.builders.FilterBuilder
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterBuilderTest {
    data class TestObject(val foo: String)

    @Test
    fun rawUsage() {
        val field = TestObject::foo
        val value = "bar"

        val original = Filters.eq(field.name, value)
        val new = FilterBuilder<TestObject>().run { field equals value }

        assertEquals(original, new, "Filter result should be the same.")
    }
}
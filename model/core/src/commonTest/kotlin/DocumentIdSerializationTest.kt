import dev.stashy.kongo.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class DocumentIdSerializationTest {
    @Serializable
    data class TestObj(@SerialName("_id") val id: DocumentId, val foo: String)

    @Test
    fun `documentId serialization`() {
        val documentId = DocumentId("test")
        val serialized = Json.encodeToString(documentId)

        assertEquals("\"test\"", serialized)
    }

    @Test
    fun `documentId inside an object`() {
        val documentId = DocumentId("test")
        val testObj = TestObj(documentId, "test")

        assertEquals("{\"_id\":\"test\",\"foo\":\"test\"}", Json.encodeToString(testObj))
    }

    @Test
    fun `documentId none`() {
        val testObj = TestObj(DocumentId.None, "test")
        val json = Json.encodeToString(testObj)
        assertEquals("{\"_id\":null,\"foo\":\"test\"}", json)

        val decoded = Json.decodeFromString<TestObj>(json)
        assertEquals(DocumentId.None, decoded.id)
    }
}

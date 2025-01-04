import dev.stashy.mongoservices.model.DocumentId
import dev.stashy.mongoservices.model.DocumentIdSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlin.test.Test
import kotlin.test.assertEquals

class DocumentIdSerializationTest {
    private val customJson = Json { serializersModule = SerializersModule { contextual(DocumentIdSerializer) } }

    @Serializable
    data class TestObj(@SerialName("_id") @Contextual val id: @Contextual DocumentId, val foo: String)

    @Test
    fun `documentId serialization`() {
        val documentId = DocumentId("test")
        val serializedDefault = Json.encodeToString(documentId)
        val serializedCustom = customJson.encodeToString(documentId)

        assertEquals("\"test\"", serializedDefault)
        assertEquals("\"test\"", serializedCustom)
    }

    @Test
    fun `contextual documentId inside an object`() {
        val documentId = DocumentId("test")
        val testObj = TestObj(documentId, "test")

        assertEquals(customJson.encodeToString(testObj), Json.encodeToString(testObj))
    }
}

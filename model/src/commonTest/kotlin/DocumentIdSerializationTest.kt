import dev.stashy.kongo.model.DocumentId
import dev.stashy.kongo.model.DocumentIdSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlin.test.Test
import kotlin.test.assertEquals

class DocumentIdSerializationTest {
    private val json = Json { serializersModule = SerializersModule { contextual(DocumentIdSerializer) } }

    @Serializable
    data class TestObj(@SerialName("_id") @Contextual val id: @Contextual DocumentId, val foo: String)

    @Test
    fun `documentId serialization`() {
        val documentId = DocumentId("test")
        val serialized = json.encodeToString(documentId)

        assertEquals("\"test\"", serialized)
    }

    @Test
    fun `contextual documentId inside an object`() {
        val documentId = DocumentId("test")
        val testObj = TestObj(documentId, "test")

        assertEquals("{\"_id\":\"test\",\"foo\":\"test\"}", json.encodeToString(testObj))
    }
}

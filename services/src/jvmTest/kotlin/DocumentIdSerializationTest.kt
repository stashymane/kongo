import dev.stashy.kongo.DocumentId
import dev.stashy.kongo.toDocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.BsonDocument
import org.bson.BsonDocumentWriter
import org.bson.codecs.EncoderContext
import org.bson.codecs.kotlinx.KotlinSerializerCodec
import org.bson.types.ObjectId
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
    fun `documentId bson serialization`() {
        val original = ObjectId()
        val documentId = original.toDocumentId()

        val codec = KotlinSerializerCodec.create<DocumentId>()!!
        val doc = BsonDocument()
        val writer = BsonDocumentWriter(doc)
        val context = EncoderContext.builder().build()

        writer.writeStartDocument()
        writer.writeName("_id")
        codec.encode(writer, documentId, context)
        writer.writeEndDocument()

        assertEquals(original, doc.getObjectId("_id").value)
    }
}

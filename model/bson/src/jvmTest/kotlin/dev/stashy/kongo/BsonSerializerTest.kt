package dev.stashy.kongo

import kotlinx.serialization.json.Json
import org.bson.BsonDocument
import org.bson.BsonDocumentReader
import org.bson.BsonDocumentWriter
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.kotlinx.KotlinSerializerCodec
import org.bson.types.ObjectId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class BsonSerializerTest {
    @Test
    fun `bson serializer`() {
        val id = ObjectId().toDocumentId()

        val codec = KotlinSerializerCodec.create<DocumentId>() ?: fail("Codec not found")
        val doc = BsonDocument()

        val writer = BsonDocumentWriter(doc)
        val ectx = EncoderContext.builder().build()

        writer.writeStartDocument()
        writer.writeName("_id")
        codec.encode(writer, id, ectx)
        writer.writeEndDocument()

        assertEquals(id.value, doc.getObjectId("_id").value.toHexString())

        val reader = BsonDocumentReader(doc)
        val dctx = DecoderContext.builder().build()

        reader.readStartDocument()
        reader.readName("_id")
        val documentId = codec.decode(reader, dctx)
        reader.readEndDocument()

        assertEquals(id, documentId)
    }

    @Test
    fun `json serializer`() {
        val id = ObjectId().toDocumentId()
        val json = Json.encodeToString(id)
        val serialized = Json.decodeFromString<DocumentId>(json)

        assertEquals(id, serialized)
    }
}

package dev.stashy.mongoservices

import dev.stashy.mongoservices.model.EntityId
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.bson.BsonObjectId
import org.bson.codecs.Codec
import org.bson.codecs.kotlinx.BsonDecoder
import org.bson.codecs.kotlinx.BsonEncoder
import org.bson.codecs.kotlinx.KotlinSerializerCodec
import org.bson.types.ObjectId

object EntityIdBsonSerializer : KSerializer<EntityId> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("EntityIdBsonSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: EntityId) {
        when (encoder) {
            is BsonEncoder -> encoder.encodeObjectId(ObjectId(value.id))
            else -> encoder.encodeString(value.id)
        }
    }

    override fun deserialize(decoder: Decoder): EntityId {
        return when (decoder) {
            is BsonDecoder -> decoder.decodeObjectId().asEntityId()
            else -> EntityId(decoder.decodeString())
        }
    }

    val entityIdCodec: Codec<EntityId>? =
        KotlinSerializerCodec.create<EntityId>(serializersModule = SerializersModule { contextual(EntityIdBsonSerializer) })
}

fun ObjectId.asEntityId(): EntityId = EntityId(this.toHexString())
fun BsonObjectId.asEntityId(): EntityId = EntityId(this.value.toHexString())
fun EntityId.asObjectId(): ObjectId = ObjectId(id)

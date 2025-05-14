import com.mongodb.kotlin.client.coroutine.MongoCollection
import dev.stashy.kongo.collection.findOneAndDelete

suspend fun test() {
    val collection: MongoCollection<String> by lazy { TODO() }

    collection.findOneAndDelete(options = { comment("Remove empty") }) {
        String::length equals 0
    }
}

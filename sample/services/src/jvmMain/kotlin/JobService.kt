import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.stashy.kongo.DocumentId
import dev.stashy.kongo.collection.find
import dev.stashy.kongo.documentId
import dev.stashy.kongo.service.KongoService
import dev.stashy.kongo.service.meta
import kotlinx.coroutines.flow.first

class JobService(
    override val database: MongoDatabase
) : KongoService<Job> {
    override val info: KongoService.Info<Job> by meta("jobs")

    suspend fun getById(id: DocumentId): Result<Job> = operation {
        find { Job::id equals id }.first()
    }

    suspend fun insert(job: Job): Result<DocumentId> = operation {
        insertOne(job).documentId!!
    }
}

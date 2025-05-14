import com.mongodb.kotlin.client.coroutine.MongoDatabase
import dev.stashy.kongo.DocumentId
import dev.stashy.kongo.collection.find
import dev.stashy.kongo.documentId
import dev.stashy.kongo.service.KongoService
import dev.stashy.kongo.service.meta
import kotlinx.coroutines.flow.first

class EmployerService(
    override val database: MongoDatabase
) : KongoService<Employer> {
    override val info: KongoService.Info<Employer> by meta("employers")

    suspend fun getById(id: DocumentId): Result<Employer> = operation {
        find { Employer::id equals id }.first()
    }

    suspend fun insert(employer: Employer): Result<DocumentId> = operation {
        insertOne(employer).documentId!!
    }
}

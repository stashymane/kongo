import dev.stashy.kongo.DocumentId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Job(
    @SerialName("_id") val id: DocumentId? = null,
    val name: String,
    val employer: DocumentId
)

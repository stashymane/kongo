import dev.stashy.kongo.DocumentId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DocumentIdTest {
    @Test
    fun `documentId building`() {
        val timestamp = 1750624582L
        val machineId = 7986493L
        val processId = 16424L
        val counter = 510844L

        val id = DocumentId("6858694679dd3d402807cb7c")
        val built = DocumentId(timestamp, machineId, processId, counter)

        assertEquals(id, built, "DocumentIds should be equal")
        assertEquals(timestamp, id.timestamp)
        assertEquals(machineId, id.machineId)
        assertEquals(processId, id.processId)
        assertEquals(counter, id.counter)
    }

    @Test
    fun `documentId timestamp comparison`() {
        val first = DocumentId(0L, 0L, 0L, 0L)
        val second = DocumentId(1L, 0L, 0L, 0L)

        assertTrue(first < second, "First value is less than second")
    }

    @Test
    fun `documentId machineId comparison`() {
        val first = DocumentId(0L, 0L, 0L, 0L)
        val second = DocumentId(0L, 1L, 0L, 0L)

        assertTrue(first < second, "First value is less than second")
    }

    @Test
    fun `documentId processId comparison`() {
        val first = DocumentId(0L, 0L, 0L, 0L)
        val second = DocumentId(0L, 0L, 1L, 0L)

        assertTrue(first < second, "First value is less than second")
    }

    @Test
    fun `documentId counter comparison`() {
        val first = DocumentId(0L, 0L, 0L, 0L)
        val second = DocumentId(0L, 0L, 0L, 1L)

        assertTrue(first < second, "First value is less than second")
    }
}

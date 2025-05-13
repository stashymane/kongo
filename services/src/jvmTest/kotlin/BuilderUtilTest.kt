import dev.stashy.kongo.builders.serialName
import kotlinx.serialization.SerialName
import kotlin.test.Test
import kotlin.test.assertEquals

class BuilderUtilTest {
    class TestClass(
        @SerialName("StringProperty")
        val prop1: String,
        @SerialName("IntProperty")
        val prop2: Int
    )

    @Test
    fun testSerialName() {
        assertEquals("StringProperty", TestClass::prop1.serialName())
        assertEquals("IntProperty", TestClass::prop2.serialName())
    }
}

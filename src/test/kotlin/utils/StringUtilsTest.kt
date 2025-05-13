package utils
import com.github.axelasports.utils.removeLastNewline
import kotlin.test.Test

class StringUtilsTest {
    @Test
    fun removeLastNewlineTest() {
        assert("test".removeLastNewline() == "test")
        assert("test\n".removeLastNewline() == "test")
        assert("test\r\n".removeLastNewline() == "test")
    }
}

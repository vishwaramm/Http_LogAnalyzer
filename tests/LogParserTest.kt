import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File

internal class LogParserTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            TestUtils().generateFiles()
        }
    }
    @Test
    fun logParser_Parse_Success() {
        val parser = LogParser()
        val logs = parser.parse("./logs/logParser_Parse_Success.csv")

        assertNotNull(logs, "logs collection returned null")

        assertTrue(logs.count() > 0, "No logs in file") //logs in file
        assertTrue(logs.count() == 100, "Should have 100 entries but instead has ${logs.count()}") //parsed all lines
    }

    @Test
    fun logParser_Parse_BadDate_Test() {
        val parser = LogParser()
        val logs = parser.parse("./logs/logParser_Parse_BadDate.csv")
        assertNotNull(logs, "logs collection returned null")

        assertTrue(logs.count() > 0, "No logs in file") //logs in file
        //3 bad dates, so 3 bad log entries, we should still have 97 entries
        val validEntries = logs.count{l -> l.isValid() }
        assertTrue(validEntries == 97, "Should have 97 entries but instead has ${validEntries}")
    }

    @Test
    fun logParser_Parse_InvalidCSV_Test() {
        val parser = LogParser()
        val logs = parser.parse("./logs/logParser_Parse_InvalidCSV.csv")

        assertNotNull(logs, "logs collection returned null")
        assertTrue(logs.count() == 0, "Invalid CSV managed to get parsed.")
    }

    @Test
    fun logParser_Parse_LargeLogs_Success() {
        val parser = LogParser()
        val logs = parser.parse("./logs/logParser_Parse_LargeLogs_Success.csv")

        assertNotNull(logs, "LogMap object returned null")

        assertTrue(logs.count() > 0, "No logs in file") //logs in file
        assertTrue(logs.count() == 10000000, "Should have 10000000 entries but instead has ${logs.count()}") //parsed all lines

    }

    @Test
    fun logParser_Parse_NoHeader_Test() {
        val parser = LogParser()
        val logs = parser.parse("./logs/logParser_Parse_NoHeader.csv")

        assertNotNull(logs, "logs collection returned null")
        assertTrue(logs.count() == 250, "Log parser didn't handle csv with no header")
    }
}
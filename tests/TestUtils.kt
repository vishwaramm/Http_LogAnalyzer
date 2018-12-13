import java.io.File

class TestUtils {
    fun generateFiles() {
        val generator = LogFileGenerator()
        var filename = "./logs/logParser_Parse_Success.csv"
        var file = File(filename)

        if (!file.exists()) {
            generator.generate(10, 100, 7, filename)
        }

        filename = "./logs/logParser_Parse_BadDate.csv"
        file = File(filename)

        if (!file.exists()) {
            generator.generate(10, 100, 7, filename, 3)
        }

        filename = "./logs/logParser_Parse_InvalidCSV.csv"
        file = File(filename)

        if (!file.exists()) {
            generator.generate(5, 20, 2, filename, 0, true)
        }

        filename = "./logs/logParser_Parse_LargeLogs_Success.csv"
        file = File(filename)

        if (!file.exists()) {
            generator.generate(1000, 10000000, 7, filename)
        }

        filename = "./logs/logParser_Parse_NoHeader.csv"
        file = File(filename)

        if (!file.exists()) {
            generator.generate(10, 250, 3, filename, noHeader = true)
        }
    }
}
import java.io.FileWriter
import java.util.Arrays
import java.util.UUID
import java.util.Random
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LogFileGenerator {
    private val HEADER = "path,user,timestamp"
    private val _pages = ArrayList<String>(Arrays.asList("/index.html", "/about.html", "/contact.html", "/products.html"))
    private val _random = Random()

    fun generate(numberOfUsers: Int,
                 numberOfEntries: Int,
                 numberOfDays: Long,
                 filename: String,
                 badDateNumber: Int = 0,
                 makeInvalid: Boolean = false,
                 noHeader: Boolean = false) {
        var fileWriter: FileWriter? = null
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        val delimiter: Char

        if (makeInvalid) {
            delimiter = ' '
        } else {
            delimiter = ','
        }

        try {
            fileWriter = FileWriter(filename)
            val users = zGenerateUsers(numberOfUsers)
            val dates = zGenerateDates(numberOfDays)
            var counter = 0
            var badDateCounter = badDateNumber

            if (!noHeader) {
                fileWriter.append(HEADER)
            }

            fileWriter.append('\n')

            while (counter < numberOfEntries) {
                val dateIndex = _random.nextInt(dates.count())
                val userIndex = _random.nextInt(numberOfUsers)
                val pageIndex = _random.nextInt(_pages.count())

                fileWriter.append(_pages[pageIndex])

                fileWriter.append(delimiter)
                fileWriter.append(users[userIndex].toString())
                fileWriter.append(delimiter)

                if (badDateCounter == 0) {
                    fileWriter.append(formatter.format(dates[dateIndex]))
                } else {
                    fileWriter.append("2017-05")
                    badDateCounter--
                }
                fileWriter.append('\n')
                counter++
            }

        } catch (e: Exception) {
            //swallow error for now
            e.printStackTrace()
        } finally {
            fileWriter!!.close()
        }
    }

    private fun zGenerateDates(numberOfDays: Long): ArrayList<ZonedDateTime> {
        val dates = ArrayList<ZonedDateTime>()
        val endDate = ZonedDateTime.now()
        var currentDate = endDate.minusDays(numberOfDays)

        while (currentDate < endDate) {
            dates.add(currentDate)
            currentDate = currentDate.plusMinutes(1)
        }

        return dates
    }

    private fun zGenerateUsers(numberOfUsers: Int): ArrayList<UUID> {
        val users = ArrayList<UUID>(numberOfUsers)

        for (i in 0..numberOfUsers) {
            users.add(UUID.randomUUID())
        }

        return users
    }
}
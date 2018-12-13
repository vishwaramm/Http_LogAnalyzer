import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Queue
import java.util.LinkedList


class LogParser {
    private val HEADER = "path,user,timestamp"

    /**
     * Parses comma separated log file in the form of path,userid,timestamp
     *
     * @param filename is the path of the http log file to be parsed
     * @return a list of log objects with the path,userid, and timestamp information
     */
    fun parse(filename: String): ArrayList<Log> {

        val logs = ArrayList<Log>()
        var chunkSize = 1000
        var chunk: Queue<String> = LinkedList<String>()

        //make it null here so finally block can access it
        var fileReader: BufferedReader? = null

        try {
            val inputStream = FileInputStream(filename)
            //create file reader with file path
            fileReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?

            //read the csv header
            line = fileReader.readLine()

            if (line.toLowerCase() == HEADER) {
                //read the next line of the log since the once we just read is the header
                line = fileReader.readLine()
            }

            //keep parsing file until there are no more
            //lines to parse
            while (line != null) {
                if (chunk.count() < chunkSize) {
                    chunk.add(line)
                } else {
                    zParseQueue(chunk, logs)
                    chunk.add(line)
                }
                //read the next line
                line = fileReader.readLine()
            }

            zParseQueue(chunk, logs)

        } catch (e: Exception) {
            //log any error with reading the file
            e.printStackTrace()
        }
        finally {
            //dispose
            fileReader?.close()
        }

        return logs;
    }

    private fun zParseQueue(chunk: Queue<String>, logs: ArrayList<Log>) {

        while (!chunk.isEmpty()) {
            val l = chunk.poll()
            val log = zParseLine(l)

            //if the line was valid, then add it to the list
            if (log != null) {

                logs.add(log)
            }
        }
    }

    /**
     * Converts comma separated line into Log object
     *
     * @param text is a comma separated line of text
     * @return Log object with parsed information
     */
    private fun zParseLine(text: String) : Log? {
        val capacity = 3
        //get each value by position
        val columns = ArrayList<String>(capacity)
        val x = "${text},"
        var iPos = 0
        var iStr = 0
        var iNext = x.indexOf(',', iPos)

        while (iNext != -1 && iStr < capacity) {
            if (iNext == iPos) {
                return null //one of the values don't exist
            } else {
                columns.add(x.substring(iPos, iNext))
            }
            iPos = iNext + 1
            iNext = x.indexOf(',', iPos)
        }

        if (columns.count() == capacity) {
            return Log(columns[0], columns[1], columns[2])
        }

        return null
    }
}
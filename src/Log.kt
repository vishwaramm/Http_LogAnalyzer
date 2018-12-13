import java.text.SimpleDateFormat
import java.util.UUID
import java.util.Date

class Log(
    val path:       String,
    val userId:     String,
    val timestamp:  String
) {
    fun isValid(): Boolean {
       try {
           UUID.fromString(userId)

           getFormattedTimestamp()

           if (path == "")
               return false
       } catch(e: Exception) {
           return false
       }

        return true
    }

    fun getFormattedTimestamp(): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
        return formatter.parse(timestamp)
    }

    fun getDatePartTimestamp() : String {
        return timestamp.substring(0, timestamp.indexOf('T'))
    }
}
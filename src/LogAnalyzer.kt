import java.text.SimpleDateFormat
import java.util.UUID
import java.util.SortedMap

class LogAnalyzer {
    private var _logs: HashMap<String, ArrayList<Log>>

    constructor(filename: String) {
        //create instance of LogParser class
        val logParser = LogParser()

        //get logs, if failure the parse function takes care of it
        val logs = logParser.parse(filename)
        _logs = zGetMapFromLogs(logs)

    }

    /**
     * Gets users by unique page views by day from http server log
     *
     * @return HashMap of dates of list of users sorted by page views descending
     */
    fun getUsersByUniquePageViews() : SortedMap<String, ArrayList<User>> {
        val map = HashMap<String, ArrayList<User>>(_logs.count())

        //for each day in the log dictionary
        for (date in _logs) {
            //get the analytics for the logs on this date
            map[date.key] = zGetUsersByUniquePageViews(date.value)
        }

        return map.toSortedMap(compareBy{ it })
    }

    /**
     * Gets pages by unique hits by day from http server log
     *
     * @return HashMap of dates of list of pages sorted by Unique Hits descending
     */
    fun getPagesByUniqueHits() : SortedMap<String, ArrayList<Page>> {
        val map = HashMap<String, ArrayList<Page>>(_logs.count())

        //for each day in the log dictionary
        for (date in _logs) {
            //get the analytics for the logs on this date
            map[date.key] = zGetPagesByUniqueHits(date.value)
        }

        return map.toSortedMap(compareBy{ it })
    }

    /**
     * Gets pages by number of users by day from http server log
     *
     * @return HashMap of dates of list of pages sorted by Number of Users descending
     */
    fun getPagesByNumberOfUsers() : SortedMap<String, ArrayList<Page>> {
        val map = HashMap<String, ArrayList<Page>>(_logs.count())

        //for each day in the log dictionary
        for (date in _logs) {
            //get the analytics for the logs on this date
            map[date.key] = zGetPagesByNumberOfUsers(date.value)
        }

        return map.toSortedMap(compareBy{ it })
    }

    private fun zGetUsersByUniquePageViews(logsForToday: ArrayList<Log>) : ArrayList<User> {
        val map = HashMap<UUID, User>()

        //loop through logs
        for (log in logsForToday)
        {
            var key = UUID.fromString(log.userId)
            //if userId exists then just add the page to the hashSet
            //else add the user and the hashSet with the current path
            if (map.containsKey(key)) {
                if (!map[key]!!.pagesViewed.contains(log.path)) {
                    map[key]!!.pagesViewed.add(log.path)
                }
            } else {
                val hSet = HashSet<String>()
                hSet.add(log.path)
                map[key] = User(key, hSet)
            }
        }

        //convert users to arrayList and sort by page views descending
        var users = ArrayList(map.values)
        users.sortByDescending{ it.pagesViewed.count() }

        return users
    }

    private fun zGetPagesByUniqueHits(logsForToday : ArrayList<Log>): ArrayList<Page> {
        val pages = zGetPagesFromLog(logsForToday)
        //sort pages by uniqueHits descending
        pages.sortByDescending{ it.uniqueHits }
        return pages
    }

    private fun zGetPagesByNumberOfUsers(logsForToday : ArrayList<Log>): ArrayList<Page> {
        val pages = zGetPagesFromLog(logsForToday)
        //sort pages by users.count() descending
        pages.sortByDescending{ it.users.count() }
        return pages
    }

    private fun zGetPagesFromLog(logsForToday : ArrayList<Log>) : ArrayList<Page> {
        var map = HashMap<String, Page>()

        //loop through logs
        for (log in logsForToday) {
            //if page exists in map, then increment uniqueHits and add user to hashSet
            //else create hashset and add user, add new path to map with new Page
            if (map.containsKey(log.path)) {
                map[log.path]!!.uniqueHits++
                map[log.path]!!.users.add(UUID.fromString(log.userId))
            } else {
                val hSet = HashSet<UUID>()
                hSet.add(UUID.fromString(log.userId))

                map[log.path] = Page(log.path, 1, hSet)
            }
        }
        //return arrayList
        return ArrayList(map.values)
    }

    private fun zGetMapFromLogs(logs: ArrayList<Log>): HashMap<String, ArrayList<Log>> {
        val map = HashMap<String, ArrayList<Log>>()

        for (log in logs) {
            val key = log.getDatePartTimestamp()

            if (map.containsKey(key)) {
                map[key]!!.add(log)
            } else {
                map[key] = ArrayList()
                map[key]!!.add(log)
            }
        }

        return map
    }
}
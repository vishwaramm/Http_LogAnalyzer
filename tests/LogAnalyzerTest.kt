import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

internal class LogAnalyzerTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            TestUtils().generateFiles()
        }
    }

    @Test
    fun logAnalyzer_GetUsersByUniquePageViews_Success() {
        val filename = "./logs/LogAnalyzer_Success.csv"
        val analyzer = LogAnalyzer(filename)
        val date = "2017-09-28"

        assertNotNull(analyzer, "Analyzer object failed to initialize")

        val usersByDay = analyzer.getUsersByUniquePageViews()

        assertNotNull(usersByDay, "users by day map returned null")
        assertNotNull(usersByDay[date], "date doesn't exist in map")
        assertTrue(usersByDay[date]!!.count() == 2, "There should be 2 users in this collection")


        val user1 = usersByDay[date]!![0]
        val user2 = usersByDay[date]!![1]
        assertTrue(user1.pagesViewed.count() == 4, "Incorrect number of pages (${user1.pagesViewed.count()}) viewed for user ${user1.userId} expected 4")
        assertTrue(user2.pagesViewed.count() == 2, "Incorrect number of pages (${user1.pagesViewed.count()}) viewed for user ${user2.userId} expected 2")

    }

    @Test
    fun logAnalyzer_GetPagesByNumberOfUsers_Success() {
        val filename = "./logs/LogAnalyzer_Success.csv"
        val analyzer = LogAnalyzer(filename)
        val date = "2017-09-28"

        assertNotNull(analyzer, "Analyzer object failed to initialize")

        val pagesByDay = analyzer.getPagesByNumberOfUsers()

        assertNotNull(pagesByDay, "users by day map returned null")
        assertNotNull(pagesByDay[date], "date doesn't exist in map")
        assertTrue(pagesByDay[date]!!.count() == 4, "There should be 4 pages in this collection")

        val page1 = pagesByDay[date]!![0]
        val page2 = pagesByDay[date]!![1]
        val page3 = pagesByDay[date]!![2]
        val page4 = pagesByDay[date]!![3]
        assertTrue(page1.users.count() == 2, "Incorrect number of users for page ${page1.path}")
        assertTrue(page2.users.count() == 2, "Incorrect number of users for page ${page2.path}")
        assertTrue(page3.users.count() == 1, "Incorrect number of users for page ${page3.path}")
        assertTrue(page4.users.count() == 1, "Incorrect number of users for page ${page4.path}")

    }

    @Test
    fun logAnalyzer_GetPagesByUniqueHits_Success() {
        val filename = "./logs/LogAnalyzer_Success.csv"
        val analyzer = LogAnalyzer(filename)
        val date = "2017-09-28"

        assertNotNull(analyzer, "Analyzer object failed to initialize")

        val pagesByDay = analyzer.getPagesByUniqueHits()

        assertNotNull(pagesByDay, "users by day map returned null")
        assertNotNull(pagesByDay[date], "date doesn't exist in map")
        assertTrue(pagesByDay[date]!!.count() == 4, "There should be 4 pages in this collection")

        val page1 = pagesByDay[date]!![0]
        val page2 = pagesByDay[date]!![1]
        val page3 = pagesByDay[date]!![2]
        val page4 = pagesByDay[date]!![3]
        assertTrue(page1.uniqueHits == 2, "Incorrect number (${page1.uniqueHits}) of hits for page ${page1.path}")
        assertTrue(page2.uniqueHits == 2, "Incorrect number (${page2.uniqueHits}) of hits for page ${page2.path}")
        assertTrue(page3.uniqueHits == 2, "Incorrect number (${page3.uniqueHits}) of hits for page ${page3.path}")
        assertTrue(page4.uniqueHits == 1, "Incorrect number (${page4.uniqueHits}) of hits for page ${page4.path}")

    }
}
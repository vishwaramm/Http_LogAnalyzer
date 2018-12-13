
    fun main(args: Array<String>) {
        val beginning = System.nanoTime()

        println("Please Wait. Reading Logs...")
        /*****************************************
         * IMPORTANT: Run Unit Tests to generate log file with 10 million records to test
         */
        val filename = "./logs/LogAnalyzer_Success.csv"//""./logs/logParser_Parse_LargeLogs_Success.csv"
        //create instance of LogAnalyzer class
        var start = System.nanoTime()
        val analyzer = LogAnalyzer(filename)
        var end = System.nanoTime()
        var milliseconds = (end - start)/1000000

        println("initialization operation took ${milliseconds} milliseconds")

        //print statistics
        start = System.nanoTime()
        zPrintPagesByUniqueHits(analyzer)
        end = System.nanoTime()
        milliseconds = (end - start)/1000000

        println("operation took ${milliseconds} milliseconds")

        start = System.nanoTime()
        zPrintPagesByNumberOfUsers(analyzer)
        end = System.nanoTime()
        milliseconds = (end - start)/1000000

        println("operation took ${milliseconds} milliseconds")

        start = System.nanoTime()
        zPrintUsersByUniquePageViews(analyzer)
        end = System.nanoTime()
        milliseconds = (end - start)/1000000

        println("operation took ${milliseconds} milliseconds")

        milliseconds = (System.nanoTime() - beginning)/1000000

        println("total time took ${milliseconds} milliseconds")
    }

    fun zPrintPagesByUniqueHits(analyzer: LogAnalyzer) {
        //get pages by unique hits statistics
        val pagesByUniqueHitDays = analyzer.getPagesByUniqueHits()
        //initialize counter
        var rank: Int

        //format output
        val leftAlignFormat = "| %-4d | %-15s | %-4d |%n"

        println("")
        println("Pages by unique hits:")

        //for each day in the logs
        for (day in pagesByUniqueHitDays) {
            //always set rank back to 1 when looping
            rank = 1
            println("Date: ${day.key.toString()}")

            System.out.format("+------+-----------------+------+%n")
            System.out.format("| Rank | Page            | Hits |%n")
            System.out.format("+------+-----------------+------+%n")

            //for each page in today's logs print the data
            for (page in day.value) {
                System.out.format(leftAlignFormat, rank, page.path, page.uniqueHits)
                //increment counter
                rank++
            }
            System.out.format("+------+-----------------+------+%n")
        }
    }

    fun zPrintPagesByNumberOfUsers(analyzer: LogAnalyzer) {
        //get pages by number of users
        val pagesByNumberOfUserDays = analyzer.getPagesByNumberOfUsers()
        //initialize counter
        var rank: Int

        //format output
        val leftAlignFormat = "| %-4d | %-15s | %-4d |%n"

        println("")
        println("Pages by number of users:")

        //for each day in the logs
        for (day in pagesByNumberOfUserDays) {
            //always set rank back to 1 when looping
            rank = 1
            println("Date: ${day.key.toString()}")
            System.out.format("+------+-----------------+-------+%n")
            System.out.format("| Rank | Page            | Users |%n")
            System.out.format("+------+-----------------+-------+%n")

            //for each page in today's logs print the data
            for (page in day.value) {
                System.out.format(leftAlignFormat, rank, page.path, page.users.count())
                //increment counter
                rank++
            }
            System.out.format("+------+-----------------+------+%n")
        }
    }

    fun zPrintUsersByUniquePageViews(analyzer: LogAnalyzer) {
        //get users by unique page views
        val usersByUniquePageViewDays = analyzer.getUsersByUniquePageViews()
        //initialize counter
        var rank: Int

        //format output
        val leftAlignFormat = "| %-4d | %-40s | %-5d |%n"

        println("")
        println("Users by unique page view: ")

        //for each day in the logs
        for (day in usersByUniquePageViewDays) {
            //always set rank back to 1 when looping
            rank = 1
            println("Date: ${day.key.toString()}")
            System.out.format("+------+------------------------------------------+-------+%n")
            System.out.format("| Rank | User                                     | Pages |%n")
            System.out.format("+------+------------------------------------------+-------+%n")

            //for each user in today's logs print the data
            for (user in day.value) {
                System.out.format(leftAlignFormat, rank, user.userId, user.pagesViewed.count())
                //increment counter
                rank++
            }
            System.out.format("+------+------------------------------------------+-------+%n")
        }
    }
import java.util.UUID

data class User(
    val userId: UUID,
    var pagesViewed: HashSet<String>
)
import java.util.UUID

data class Page (
    var path: String,
    var uniqueHits: Int,
    val users: HashSet<UUID>
)
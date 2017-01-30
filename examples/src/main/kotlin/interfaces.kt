import java.util.*

interface Review {
  val id: String
  val timestamp: Date
  val finished: Boolean

  val author: Person

  val commits: Array<out Commit>
}

interface Commit {
  val id: String
}

interface Person {
  val name: String
  val experience: Int
}
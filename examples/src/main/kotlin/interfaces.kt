import com.github.kropp.jsonex.Json
import java.util.*

@Json
interface Review {
  val id: String
  val timestamp: Date
  val finished: Boolean

  val author: Person

  val commits: Array<out Commit>
}

@Json
interface Commit {
  val id: String
}

@Json
interface Person {
  val name: String
  val experience: Int
}
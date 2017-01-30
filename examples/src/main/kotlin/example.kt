import java.util.*
import com.github.kropp.jsonex.*

interface Review {
  val id: String
  val timestamp: Date
  val finished: Boolean

  val author: Person

  val commits: Array<out Commit>
}
interface MutableReview : Review {
  override val id: String
  override val timestamp: Date
  override val finished: Boolean

  override val author: MutablePerson

  override val commits: Array<out Commit>
}

class ReviewImpl(map: Map<String,Any> = mapOf()) : Review, JsonObject<ReviewImpl>(map) {
  override val id by string()
  override val timestamp by date()
  override val finished by bool()

  override val author by PersonImpl()

  override val commits by array<Commit>()
}

class MutableReviewImpl(map: MutableMap<String,Any> = mutableMapOf()) : MutableReview, MutableJsonObject<MutableReviewImpl>(map) {
  override var id by string()
  override var timestamp by date()
  override var finished by bool()

  override var author by MutablePersonImpl()

  override var commits by array<Commit>()
}

interface Commit {
  val id: String
}
interface MutableCommit : Commit {
  override var id: String
}

class CommitImpl(map: Map<String,Any> = mapOf()) : Commit, JsonObject<CommitImpl>(map) {
  override val id by string()
}

class MutableCommitImpl : MutableCommit, MutableJsonObject<MutableCommitImpl>() {
  override var id by string()
}

interface Person {
  val name: String
  val experience: Int
}
interface MutablePerson : Person {
  override var name: String
  override var experience: Int
}

class PersonImpl(map: MutableMap<String,Any> = mutableMapOf()) : Person, JsonObject<PersonImpl>(map) {
  override val name by string()
  override val experience by int()
}

class MutablePersonImpl(map: MutableMap<String,Any> = mutableMapOf()) : MutablePerson, MutableJsonObject<MutablePersonImpl>(map) {
  override var name by string()
  override var experience by int()
}


fun person(builder: MutablePerson.() -> Unit): Person = PersonImpl(MutablePersonImpl().apply(builder)._map)
fun review(builder: MutableReviewImpl.() -> Unit) = ReviewImpl(MutableReviewImpl().apply(builder)._map)
fun commit(builder: MutableCommit.() -> Unit): Commit = CommitImpl(MutableCommitImpl().apply(builder)._map)


fun main(args: Array<String>) {
  val r = review {
    id = "1-1-1-1-1"
    timestamp = Date()
    finished = true

    commits = arrayOf(commit { id = "abc123" }, commit { id = "321cba" })

    author {
      name = "John Doe"
    }
  }
  println(r)
  println(r.commits[1])

  val r2 = ReviewImpl(mapOf(
      "id" to "1",
      "author" to mapOf(
          "name" to "Jane Doe"
      ),
      "some" to mapOf(
          "other" to true
      )
  ))

  println(r2)
  println(r2.author.name)
}
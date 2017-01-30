import com.github.kropp.jsonex.*
import java.util.*

interface MutableReview : Review {
  override var id: String
  override var timestamp: Date
  override var finished: Boolean

  override var author: MutablePerson
  fun author(builder: MutablePerson.() -> Unit) = author.apply(builder)

  override var commits: Array<out Commit>
}

class ReviewImpl(map: Map<String,Any> = mapOf()) : Review, JsonObject<ReviewImpl>(map) {
  override val id by string()
  override val timestamp by date()
  override val finished by bool()

  override val author by PersonImpl()

  override val commits by array<Commit>()
}

class MutableReviewImpl(map: MutableMap<String,Any> = mutableMapOf()) : MutableReview, MutableJsonObject<MutableReviewImpl, MutableReview>(map) {
  override var id by string()
  override var timestamp by date()
  override var finished by bool()

  override var author: MutablePerson by MutablePersonImpl()

  override var commits by array<Commit>()
}

interface MutableCommit : Commit {
  override var id: String
}

class CommitImpl(map: Map<String,Any> = mapOf()) : Commit, JsonObject<CommitImpl>(map) {
  override val id by string()
}

class MutableCommitImpl : MutableCommit, MutableJsonObject<MutableCommitImpl, MutableCommit>() {
  override var id by string()
}
interface MutablePerson : Person {
  override var name: String
  override var experience: Int
}

class PersonImpl(map: MutableMap<String,Any> = mutableMapOf()) : Person, JsonObject<PersonImpl>(map) {
  override val name by string()
  override val experience by int()
}

class MutablePersonImpl(map: MutableMap<String,Any> = mutableMapOf()) : MutablePerson, MutableJsonObject<MutablePersonImpl, MutablePerson>(map) {
  override var name by string()
  override var experience by int()
}

fun person(builder: MutablePerson.() -> Unit): Person = PersonImpl(MutablePersonImpl().apply(builder)._map)
fun review(builder: MutableReview.() -> Unit) = ReviewImpl(MutableReviewImpl().apply(builder)._map)
fun commit(builder: MutableCommit.() -> Unit): Commit = CommitImpl(MutableCommitImpl().apply(builder)._map)
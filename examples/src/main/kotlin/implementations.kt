import com.github.kropp.jsonex.*
import java.util.*

interface ReviewBuilder : Review {
  override var id: String
  override var timestamp: Date
  override var finished: Boolean

  override var author: PersonBuilder
  fun author(builder: PersonBuilder.() -> Unit) = author.apply(builder)

  override var commits: Array<out Commit>
}

class ReviewImpl(map: Map<String,Any> = mapOf()) : Review, JsonObject<ReviewImpl>(map) {
  override val id by string()
  override val timestamp by date()
  override val finished by bool()

  override val author by PersonImpl()

  override val commits by array<Commit>()
}

class ReviewBuilderImpl(map: MutableMap<String,Any> = mutableMapOf()) : ReviewBuilder, JsonObjectBuilder<ReviewBuilderImpl, ReviewBuilder>(map) {
  override var id by string()
  override var timestamp by date()
  override var finished by bool()

  override var author: PersonBuilder by PersonBuilderImpl()

  override var commits by array<Commit>()
}

interface CommitBuilder : Commit {
  override var id: String
}

class CommitImpl(map: Map<String,Any> = mapOf()) : Commit, JsonObject<CommitImpl>(map) {
  override val id by string()
}

class CommitBuilderImpl : CommitBuilder, JsonObjectBuilder<CommitBuilderImpl, CommitBuilder>() {
  override var id by string()
}
interface PersonBuilder : Person {
  override var name: String
  override var experience: Int
}

class PersonImpl(map: MutableMap<String,Any> = mutableMapOf()) : Person, JsonObject<PersonImpl>(map) {
  override val name by string()
  override val experience by int()
}

class PersonBuilderImpl(map: MutableMap<String,Any> = mutableMapOf()) : PersonBuilder, JsonObjectBuilder<PersonBuilderImpl, PersonBuilder>(map) {
  override var name by string()
  override var experience by int()
}

fun person(builder: PersonBuilder.() -> Unit): Person = PersonImpl(PersonBuilderImpl().apply(builder)._map)
fun review(builder: ReviewBuilder.() -> Unit): Review = ReviewImpl(ReviewBuilderImpl().apply(builder)._map)
fun commit(builder: CommitBuilder.() -> Unit): Commit = CommitImpl(CommitBuilderImpl().apply(builder)._map)
import java.util.*
import com.github.kropp.jsonex.*

class Review(map: Map<String,Any> = mapOf()) : JsonObject<Review>(map) {
  val id by string()
  val timestamp by date()
  val finished by bool()

  val author by Person()

  val commits by array<Commit>()
}

class MutableReview(map: MutableMap<String,Any> = mutableMapOf()) : MutableJsonObject<MutableReview>(map) {
  var id by string()
  var timestamp by date()
  var finished by bool()

  var author by MutablePerson()

  var commits by array<Commit>()
}

class Commit(map: Map<String,Any> = mapOf()) : JsonObject<Commit>(map) {
  val id by string()
}

class MutableCommit : MutableJsonObject<MutableCommit>() {
  var id by string()
}

class Person(map: MutableMap<String,Any> = mutableMapOf()) : JsonObject<Person>(map) {
  val name by string()
  val experience by int()
}

class MutablePerson(map: MutableMap<String,Any> = mutableMapOf()) : MutableJsonObject<MutablePerson>(map) {
  var name by string()
  var experience by int()
}


fun person(builder: MutablePerson.() -> Unit) = Person(MutablePerson().apply(builder)._map)
fun review(builder: MutableReview.() -> Unit) = Review(MutableReview().apply(builder)._map)
fun commit(builder: MutableCommit.() -> Unit) = Commit(MutableCommit().apply(builder)._map)


fun main(args: Array<String>) {
  val r = review {
    id = "1-1-1-1-1"
    timestamp = Date()
    finished = true

    commits = arrayOf(commit { id = "abc123" }, commit { id = "321cba" })

/*
    author = person {
      name = "Jon Doe"
    }
    // or
*/
    author {
      name = "John Doe"
    }
  }
  println(r)
  println(r.commits[1])

  val r2 = Review(mapOf(
      "id" to "1",
      "author" to mapOf(
          "name" to "Jane Doe"
      ),
      "some" to mapOf(
          "other" to true
      )
  ))
/*
  r2.author {
    experience = 10
  }
*/
  println(r2)
  println(r2.author.name)
}
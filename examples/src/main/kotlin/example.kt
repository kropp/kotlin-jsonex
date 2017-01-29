import java.util.*
import com.github.kropp.jsonex.*

class Review(map: Map<String,Any> = mapOf()) : JsonObject<Review>(HashMap<String,Any>(map)) {
  var id by string()
  var timestamp by date()
  var finished by bool()

  var author by Person()

  var commits by array<Commit>()
}

class Commit : JsonObject<Commit>() {
  var id by string()
}

class Person(map: Map<String,Any> = mapOf()) : JsonObject<Person>(HashMap<String,Any>(map)) {
  var name by string()
  var experience by int()
}


fun person(builder: Person.() -> Unit) = Person().apply(builder)
fun review(builder: Review.() -> Unit) = Review().apply(builder)
fun commit(builder: Commit.() -> Unit) = Commit().apply(builder)

fun main(args: Array<String>) {
  val r = review {
    id = "1-1-1-1-1"
    timestamp = Date()
    finished = true

    commits = arrayOf(commit { id = "abc123" }, commit { id = "321cba" })

    author = person {
      name = "Jon Doe"
    }
    // or
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
  r2.author {
    experience = 10
  }
  println(r2)
  println(r2.author.name)
}
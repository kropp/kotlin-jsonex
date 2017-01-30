import java.util.*


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
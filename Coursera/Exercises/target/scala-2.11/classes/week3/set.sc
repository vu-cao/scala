import week3.NonEmpty
import week3.Empty
object set {
  val x = new NonEmpty(5, new NonEmpty(3, Empty, new NonEmpty(4, Empty, Empty)), new NonEmpty(20, new NonEmpty(13, Empty, Empty), new NonEmpty(17, new NonEmpty(15, Empty, Empty), new NonEmpty(19, Empty, Empty))))
  val y = new NonEmpty(10, new NonEmpty(7, new NonEmpty(5, Empty, Empty), new NonEmpty(8, Empty, Empty)), new NonEmpty(14, new NonEmpty(11, Empty, Empty), new NonEmpty(12, Empty, Empty)))

  val z = x union y
}
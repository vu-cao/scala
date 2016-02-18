import week3._
object session4 {
  val list = new Cons[Int](1, new Cons(2, new Cons(3, new Cons(4, new Nil[Int]))))
  list.nth(5)
}
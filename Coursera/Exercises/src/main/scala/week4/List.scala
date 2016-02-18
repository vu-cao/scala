package week4

import week3._

/**
  * Created by Vu on 2/18/2016.
  */
object List {
  def apply[T](x: T): List[T] = {
    new Cons[T](x, new Nil[T])
  }
  def apply[T](x: T, y: T): List[T] = {
    new Cons[T](x, new Cons(y, new Nil[T]))
  }
  def apply[T](): Nil[T] = new Nil
}

package week4.idealized

/**
  * Created by Vu on 2/23/2016.
  */
abstract class Boolean {
  def IfThenElse[T](t: => T, e: => T): T

  def < (that: Boolean): Boolean = {
    IfThenElse[Boolean](newFalse, that)
  }
}

object newTrue extends Boolean {
  def IfThenElse[T](t: => T, e: => T): T = t
}

object newFalse extends Boolean {
  override def IfThenElse[T](t: => T, e: => T): T = e
}


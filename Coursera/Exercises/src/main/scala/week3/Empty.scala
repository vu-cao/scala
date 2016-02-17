package week3

/**
  * Created by Vu on 2/17/2016.
  */
object Empty extends IntSet {
  def contains(x: Int) = false

  def incl(x: Int) = new NonEmpty(x, Empty, Empty)

  override def union(other: IntSet): IntSet = other

  override def toString = "."
}

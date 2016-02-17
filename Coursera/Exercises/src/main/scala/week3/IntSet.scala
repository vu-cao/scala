package week3

/**
  * Created by Vu on 2/17/2016.
  */
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

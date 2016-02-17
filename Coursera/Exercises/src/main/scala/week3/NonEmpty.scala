package week3

/**
  * Created by Vu on 2/17/2016.
  */
class NonEmpty(element: Int, left: IntSet, right: IntSet) extends IntSet {
  val e = element
  val l = left
  val r = right

  override def incl(x: Int): IntSet = {
    if (x < element) new NonEmpty(element, left incl x, right)
    else {
      if (x > element) new NonEmpty(element, left, right incl x)
      else this
    }
  }

  override def contains(x: Int): Boolean = {
    if (x < element) left contains x
    else {
      if (x > element) right contains x
      else true
    }
  }

  override def union(other: IntSet): IntSet = {
    if (other == Empty) this
    else {
      other union left
      other union right
      other incl element
    }
  }

  override def toString: String = {
    "{" + left + element + right + "}"
  }
}

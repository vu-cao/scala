package week3

/**
  * Created by Vu on 2/18/2016.
  */
trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  def size: Int
  def nth(n: Int): T
}

class Nil[T] extends List[T] {
  def isEmpty = true
  def head = throw new NoSuchFieldException("Nil.head")
  def tail = throw new NoSuchElementException("Nil.tail")
  def size = 0
  def nth(n: Int) = throw new NoSuchElementException("Nil.nth")
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  override def isEmpty = false
  override def size = {
    def calculateSize(value: Int, list: List[T]): Int = {
      if (this isEmpty) value
      else calculateSize(value + 1, this.tail)
    }
    calculateSize(0, this)
  }

  override def nth(n: Int): T = {
    def getElementAt(x: Int, list: List[T]): T = {
      if (list.isEmpty) throw new IndexOutOfBoundsException("Out of bound");
      else {
        if (x == 0) list.head
        else {
          getElementAt(x - 1, list.tail)
        }
      }
    }

    getElementAt(n, this)
  }
}

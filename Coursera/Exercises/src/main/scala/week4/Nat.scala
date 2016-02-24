package week4

/**
  * Created by Vu on 2/23/2016.
  */
abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor: Nat
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}

object Zero extends Nat {
  def isZero: Boolean = true
  def predecessor: Nat = throw new NoSuchMethodError("Zero.predecessor")
  def successor: Nat = new Succ(this)
  def -(that: Nat): Nat = if (that.isZero) this else throw new NoSuchMethodError("Zero.-")
  def +(that: Nat): Nat = that
}

class Succ(n: Nat) extends Nat {
  def isZero: Boolean = false
  def successor: Nat = new Succ(this)
  def predecessor: Nat = n
  def + (that: Nat): Nat = {
    val result = successor
    if (!that.isZero) result + that.predecessor
    else result
  }
  def -(that: Nat): Nat = {
    val result = predecessor
    if (!that.isZero) result - that.predecessor
    else result
  }
}
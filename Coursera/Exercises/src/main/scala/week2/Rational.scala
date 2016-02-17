package week2

/**
  * Created by Vu on 2/16/2016.
  */
class Rational(val a: Int, val b: Int) {
  val g = gcd(a, b)
  def + (that: Rational) = new Rational(a*that.b + that.a*b, b*that.b)

  def unary_- = new Rational(-a, b)

  def - (that: Rational) = this + -that

  def max(that: Rational): Rational = {
    val tmp: Rational = this - that
    if (tmp.a > 0) this
    else that
  }

  def less(that: Rational): Boolean = {
    val tmp = this - that
    if (tmp.a < 0) true
    else false
  }

  private def gcd(a: Int, b: Int): Int = {
    if (b == 0) a
    else gcd(b, a % b)
  }

  override def toString = {
    (a / g).toString + "/" + (b / g).toString
  }
}

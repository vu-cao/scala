package week2

/**
  * Created by Vu on 2/16/2016.
  */
class Rational(x: Int, y: Int) {
  val a = x
  val b = y

  def + (that: Rational) = new Rational(a*that.b + that.b*a, b*that.b)

  def unary_- = new Rational(-x, y)

  def - (that: Rational) = this + -that

  override def toString = a.toString + "/" + b.toString
}

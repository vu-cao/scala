import math.abs

object session4 {
  val esp = 0.0000000001
  def fixpoint(f: Double => Double)(ini: Double): Double = {
    val value = f(ini)
    if ((abs(value - ini) / ini) < esp) value
    else fixpoint(f)(value)
  }

//  fixpoint(x => 1 + x/2)(1)

  def sqrt(n: Double) = fixpoint(x => ((x + n/x)/2))(1)

  sqrt(2)

  def fixpoint2(f: (Double, (Double) => Double) => Double)(ini: Double): Double = {
    val value = f(ini, Double => Double)

    if (value < esp) value
    else fixpoint2(f)(value)
  }

  def f(x: Double): Double = x*x

  def sqrt2(n: Double) = fixpoint2(n, f(n))(1)
}
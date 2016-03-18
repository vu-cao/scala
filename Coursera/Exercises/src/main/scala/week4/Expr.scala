package week4

/**
  * Created by Vu on 2/25/2016.
  */
trait Expr {
  def isProd: Boolean
  def isVar: Boolean
  def isNumber: Boolean
  def isSum: Boolean
  def numValue: Int
  def leftOp: Expr
  def rightOp: Expr
  def eval(): Int = {
    this match {
      case Number(n) => n
      case Sum(e1, e2) => e1.eval + e2.eval
      case Var(x) => x.toInt
      case Prod(e1, e2) => e1.eval * e2.eval
    }
  }
  def show(): String = {
    this match {
      case Number(n) => n.toString
      case Sum(e1, e2) => e1.show + " + " + e2.show
      case Var(x) => x
      case Prod(e1, e2) => {
        def matchProd(e: Expr): String = {
          e match {
            case Sum(_, _) => "(" + e1.show + ")"
            case Var(x) => e.show
            case Number(n) => e.show
            case Prod(e1, e2) => e1.show + " * " + e2.show
          }
        }
        matchProd(e1) + " * " + matchProd(e2)
      }
    }
  }
}

case class Number(n: Int) extends Expr {
  override def isNumber: Boolean = true
  override def isProd: Boolean = false
  override def isVar: Boolean = false

  override def isSum: Boolean = false
  override def numValue: Int = n
  override def leftOp: Expr = throw new NoSuchMethodError("Number.leftOp")

  override def rightOp: Expr = throw new NoSuchMethodError("Number.leftOp")
}

case class Sum(e1: Expr, e2: Expr) extends Expr {
  override def isNumber: Boolean = false

  override def rightOp: Expr = e2

  override def isProd: Boolean = false

  override def isVar: Boolean = false

  override def numValue: Int = throw new NoSuchMethodError("Sum.numValue")

  override def isSum: Boolean = true

  override def leftOp: Expr = e1
}

case class Prod(e1: Expr, e2: Expr) extends Expr {
  override def isNumber: Boolean = false

  override def isProd: Boolean = true

  override def isVar: Boolean = false

  override def rightOp: Expr = e1

  override def numValue: Int = throw new NoSuchMethodError("Var.numValue")

  override def isSum: Boolean = false

  override def leftOp: Expr = e2
}

case class Var(x: String) extends Expr {
  override def isProd: Boolean = false

  override def rightOp: Expr = throw new NoSuchMethodError("Var.rightOp")

  override def numValue: Int = throw new NoSuchMethodError("Var.numValue")

  override def isNumber: Boolean = false

  override def isVar: Boolean = true

  override def isSum: Boolean = false

  override def leftOp: Expr = throw new NoSuchMethodError("Var.left")
}

object Main {
  def main(args: Array[String]): Unit = {
    val a: Number = new Number(10)
    val bc: Sum = new Sum(new Number(2), new Number(3))
    val x: Expr = Sum(Prod(Number(2), Var("x")), Var("y"))
    val y: Expr = Prod(Sum(Number(2), Var("x")), Var("y"))
    println(bc.show)
    println(x.show)
    println(y.show)
  }
}
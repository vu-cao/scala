object session2 {
  def sum(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int = {
      if (a > b) {
        0
      } else {
        f(a) + sumF(a+1, b)
      }
    }
    sumF
  }

  sum(x => x)(1, 5)
  sum(x => x * x)(1,5)

  def newSum(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) {
      0
    } else {
      f(a) + newSum(f)(a+1, b)
    }
  }
  newSum(x => x)(1, 5)
  newSum(x => x * x)(1,5)

  val f = sum(x => x *x)
  f(1,2)


  def newSum1(f: Int => Int) = {
    (a: Int, b: Int) = {
      if (a > b) {
        0
      } else {
        f(a) + newSum(f)(a+1, b)
      }
    }
  }

  newSum1(x => x)(1, 5)

}
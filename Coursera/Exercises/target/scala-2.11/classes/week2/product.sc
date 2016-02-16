object session3 {
  def product(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) {
      1
    } else {
      f(a) * product(f)(a+1, b)
    }
  }

  product(x => x)(1, 5)

  def factorial(n: Int) = product(x => x)(1, n)

  factorial(5)

  def func(f: Int => Int, unit: Int, g: (Int, Int) => Int)(a: Int, b: Int): Int = {
    if (a > b) {
      unit
    } else {
      g(f(a), func(f, unit, g)(a + 1, b))
    }
  }

  func(x=>x, 1, (a, b) => a * b)(1, 5)
}
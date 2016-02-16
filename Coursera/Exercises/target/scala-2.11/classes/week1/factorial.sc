object exercise {
  def factorial(x: Int) = {
    def calculate(x: Int, remain: Int): Long = {
      if (remain == 1) {
        x
      } else {
        return calculate(x * remain, remain-1)
      }
    }

    calculate(x, x-1)
  }

  factorial(10)
}
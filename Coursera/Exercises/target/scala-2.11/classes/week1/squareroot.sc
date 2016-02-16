import math.abs

def squareRoot(x: Int, initial: Int) = {
  def calculate(value: Double): Double = {
    def est = (value + x/value) /2
    if (isGoodEnough(est)) {
      est
    } else {
      return calculate(est)
    }
  }

  def isGoodEnough(est: Double) = {
    abs(est * est - x) < 0.00000001
  }
  calculate(initial)
}

squareRoot(2, 1)
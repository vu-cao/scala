object session {
  def sum(f: Int => Int, a: Int, b: Int): Int = {
    def loop(acc: Int, a: Int): Int = {
      if (a > b) {
        acc
      } else {
        return loop(acc + f(a), a+1)
      }
    }
    loop(0, a)
  }

  sum(x => x, 1, 5)
  sum(x => x * x, 1,5)
}
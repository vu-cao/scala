def init[T](xs: List[T]): List[T] = xs match {
  case Nil => throw new Error("Empty list")
  case x::Nil => Nil
  case head::tail => head::init(tail)
}

init(List(1, 2, 3, 4, 5))

def removeAt[T](xs: List[T], n: Int): List[T] = {
  def execute[T](xs: List[T], n: Int, current: Int): List[T] = {
    xs match {
      case Nil => Nil
      case head::tail =>
        if (n != current) head::execute(tail, n+1, current)
        else execute(tail, n+1, current)
    }
  }
  execute(xs, n, 0)
}

removeAt(List(0, 1, 2, 3, 4), 3)



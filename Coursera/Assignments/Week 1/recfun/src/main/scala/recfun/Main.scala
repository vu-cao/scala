package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
//    def calculate(value: Int, c1: Int, c2: Int, r: Int): Int = {
//      if (c1 == 0 || r == c) {
//        value + 1
//      } else {
//        return pascal(, c-1, c, r-1)
//      }
//    }
    if (c == 0 || r == c) {
      1
    } else {
      return pascal(c-1, r-1) + pascal(c, r-1)
    }
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def checkBalance(head: Char, tail: List[Char], count: Int): Boolean = {
      var v = count
      if (head == '(') {
        v += 1
      } else {
        if (head == ')') {
          v -= 1
        }
      }
      if (v < 0) {
        false
      } else {
        if (tail.isEmpty) {
          if (v != 0) {
            false
          } else {
            true
          }
        } else {
          return checkBalance(tail.head, tail.tail, v)
        }
      }
    }
    if (chars.isEmpty) {
      true
    } else {
      checkBalance(chars.head, chars.tail, 0)
    }
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def checkPosible(money: Int,coins: List[Int]): Boolean ={
      val remain = money - coins.head
      if (remain == 0) {
        true
      } else {
        if (remain < 0) {
          false
        } else {
          if (coins.tail.isEmpty) {
            false
          } else {
            return checkPosible(remain, coins.tail)
          }
        }
      }
    }
    def calculate(money: Int, coins:List[Int], ways: Int): Int = {
      var counts = ways
      if (checkPosible(money, coins)) {
        counts += 1
      }

    }

    calculate(money, coins, 0)
  }
}

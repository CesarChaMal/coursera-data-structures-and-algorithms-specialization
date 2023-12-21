import scala.annotation.tailrec
import scala.io.StdIn
import scala.collection.mutable.Queue
import scala.util.Random

object FibonacciLastDigitScala {

  // O(n) time complexity
  def getFibonacciLastDigitNaive(n: Int): Int = {
    if (n <= 1) return n

    var prev = 0
    var current = 1
    for (_ <- 2 to n) {
      val tmp_previous = prev
      prev = current
      current = tmp_previous + current
    }
    current % 10
  }

  // O(n) time complexity
  def getFibonacciLastDigit(n: Int): Int = {
    if (n <= 1) return n

    var prev = 0
    var current = 1
    for (_ <- 2 to n) {
      val tmp_previous = prev
      prev = current
      current = (tmp_previous + current) % 10 // Compute only the last digit
    }
    current
  }

  // O(n) time complexity, functional approach
  def getFibonacciLastDigitFunctionalNaive(n: Int): Int = {
    Stream.iterate((0, 1)) { case (prev, curr) => (curr, (prev + curr) % 10) }
      .map(_._1)
      .take(n + 1)
      .last
  }

  // O(n) time complexity, functional approach
  def getFibonacciLastDigitFunctional(n: Int): Int = {
    Stream.iterate((0, 1)) { case (prev, curr) => (curr, (prev + curr) % 10) }
      .map(_._1)
      .take(n + 1)
      .last
  }

  def main(args: Array[String]): Unit = {
    val n = readInt()
    println(getFibonacciLastDigit(n))
  }

  // Read a single integer from standard input
  def readInt(): Int = StdIn.readInt()

  // Read a line of text from standard input
  def readLine(): String = StdIn.readLine()
}

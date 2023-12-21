import scala.annotation.tailrec
import scala.io.StdIn
import scala.collection.mutable.Queue
import scala.util.Random

object FibonacciLastDigitScalaStressTest {

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

  // Functional interface for strategies
  type FibonacciStrategy = Int => Int

  // Method to compare strategies
  def compareStrategies(strategy1: FibonacciStrategy, strategy2: FibonacciStrategy, n: Int): Boolean = {
    val result1 = strategy1(n)
    val result2 = strategy2(n)
    if (result1 != result2) {
      println(s"Discrepancy found: $result1 != $result2")
      false
    } else {
      true
    }
  }

  // Stress test
  def stressTest(maxIterations: Int, maxN: Int): Unit = {
    val random = new Random()

    for (iteration <- 1 to maxIterations) {
      val n = random.nextInt(maxN) // Limit n to a reasonable range for the naive method

      println(s"Testing with n = $n")

      val strategies = Seq(
        getFibonacciLastDigitNaive _,
        getFibonacciLastDigit _,
        getFibonacciLastDigitFunctionalNaive _,
        getFibonacciLastDigitFunctional _
      )

      val results = strategies.map(_(n))
      if (results.distinct.size != 1) {
        println(s"Discrepancy found: ${results.mkString(", ")}")
        return
      }

      println("OK")
    }
  }

  def main(args: Array[String]): Unit = {
    stressTest(100000, 40) // Run the stress test

    // Uncomment to run with user input
    // val n = readInt()
    // println(calcFib(n))
  }

  // Read a single integer from standard input
  def readInt(): Int = StdIn.readInt()

  // Read a line of text from standard input
  def readLine(): String = StdIn.readLine()
}

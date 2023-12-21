import scala.annotation.tailrec
import scala.io.StdIn
import scala.collection.mutable.Queue
import scala.util.Random

object FibonacciScalaStressTest {

  // O(2^n) time complexity
  def calcFibNaive(n: Int): Int = {
    if (n <= 1) n
    else calcFibNaive(n - 1) + calcFibNaive(n - 2)
  }

  // O(n) time complexity
  def calcFib(n: Int): Int = {
    if (n <= 1) return n

    var prev = 0
    var current = 1
    for (_ <- 2 to n) {
      val newCurrent = prev + current
      prev = current
      current = newCurrent
    }
    current
  }

  // O(2^n) time complexity, functional naive approach
  def calcFibFunctionalNaive(n: Int): Int = {
    @tailrec
    def fibHelper(n: Int, a: Int = 0, b: Int = 1): Int = n match {
      case 0 => a
      case _ => fibHelper(n - 1, b, a + b)
    }

    fibHelper(n)
  }

  // O(n) time complexity, functional approach
  def calcFibFunctional(n: Int): Int = {
    LazyList.iterate((0, 1)) { case (a, b) => (b, a + b) }
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
        calcFibNaive _,
        calcFib _,
        calcFibFunctionalNaive _,
        calcFibFunctional _
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

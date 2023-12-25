import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Random

object GCDScalaStressTest {

  // O(min(a, b)) time complexity
  def gcd_naive(a: Long, b: Long): Long = {
    var current_gcd = 1
    var d = 2
    while (d <= a && d <= b) {
      if (a % d == 0 && b % d == 0) if (d > current_gcd) current_gcd = d
      d += 1
    }
    return current_gcd
  }

  // O(log(min(a, b))) time complexity
  // Euclidean Algorithm
  def gcd(a: Long, b: Long): Long = {
    if (b == 0) a else gcd(b, a % b)
  }

  // O(min(a, b)) time complexity
  def gcdFunctionalNaive(a: Long, b: Long): Long = {
    @tailrec
    def gcdHelper(currentGcd: Long, d: Long): Long = {
      if (d > Math.min(a, b)) currentGcd
      else {
        val newGcd = if (a % d == 0 && b % d == 0) d else currentGcd
        gcdHelper(newGcd, d + 1)
      }
    }

    gcdHelper(1, 2)
  }

  // O(log(min(a, b))) time complexity
  // Euclidean Algorithm
  def gcdFunctional(a: Long, b: Long): Long = {
    @tailrec
    def euclidGcd(x: Long, y: Long): Long = {
      if (y == 0) x else euclidGcd(y, x % y)
    }

    euclidGcd(a, b)
  }

  // Functional interface for strategies
  type GCDStrategy = (Long, Long) => Long

  // Method to compare strategies
  def compareStrategies(strategy1: GCDStrategy, strategy2: GCDStrategy, a: Long, b: Long): Boolean = {
    val result1 = strategy1(a, b)
    val result2 = strategy2(a, b)
    if (result1 != result2) {
      println(s"Discrepancy found: $result1 != $result2")
      false
    } else {
      true
    }
  }

  // Stress test
  def stressTest(maxIterations: Int): Unit = {
    val maxRange = 100_000_000L // For example, up to 100 million
    val random = new Random()

    for (iteration <- 1 to maxIterations) {
      val a = Math.abs(random.nextLong()) % maxRange
      val b = Math.abs(random.nextLong()) % maxRange

      println(s"Testing with a = $a and b = $b")

      val strategies = Seq(
        gcd_naive _,
        gcd _,
        gcdFunctionalNaive _,
        gcdFunctional _
      )

      val results = strategies.map(strategy => strategy(a, b))
      if (results.distinct.size != 1) {
        println(s"Discrepancy found: ${results.mkString(", ")}")
        return
      }

      println("OK")
    }
  }

  def main(args: Array[String]): Unit = {
    stressTest(100_000) // Run the stress test
  }

  // Read a single integer from standard input
  def readInt(): Int = StdIn.readInt()

  // Read a line of text from standard input
  def readLine(): String = StdIn.readLine()
}

import GCDScalaStressTest.{gcd, gcdFunctional, gcdFunctionalNaive, gcd_naive}

import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.Random

object LCMScalaStressTest {

  // O(a * b) time complexity - Naive Approach
  def lcm_naive1(a: Long, b: Long): Long = {
    @tailrec
    def findLcm(current: Long, limit: Long): Long = {
      if (current > limit) limit
      else if (current % a == 0 && current % b == 0) current
      else findLcm(current + 1, limit)
    }

    findLcm(1, a * b)
  }

  // O(a * b) time complexity - Alternative Naive LCM
  def lcm_naive2(a: Long, b: Long): Long = {
    var lcm = 1L
    while (true) {
      if (lcm % a == 0 && lcm % b == 0) {
        return lcm
      }
      lcm += 1
    }
    lcm // This line is technically unreachable but required for compilation
  }

  // O(log(min(a, b))) time complexity - Euclidean Algorithm for GCD
  def gcd(a: Long, b: Long): Long = {
    if (b == 0) a else gcd(b, a % b)
  }

  // O(log(min(a, b))) time complexity - Efficient LCM using GCD
  def lcm(a: Long, b: Long): Long = {
    (a * b) / gcd(a, b)
  }

  // O(a * b) time complexity - Functional Naive Approach for LCM
  def lcmFunctionalNaive(a: Long, b: Long): Long = {
    @tailrec
    def lcmHelper(current: Long, limit: Long): Long = {
      if (current > limit) limit
      else if (current % a == 0 && current % b == 0) current
      else lcmHelper(current + 1, limit)
    }

    lcmHelper(1, a * b)
  }

  // Functional approach for GCD
  def gcdFunctional(a: Long, b: Long): Long = {
    @tailrec
    def euclidGcd(x: Long, y: Long): Long = {
      if (y == 0) x else euclidGcd(y, x % y)
    }

    euclidGcd(a, b)
  }

  // O(log(min(a, b))) time complexity - Efficient LCM using GCD (Functional)
  def lcmFunctional(a: Long, b: Long): Long = {
    (a * b) / gcdFunctional(a, b)
  }

  // Functional interface for strategies
  type LCMStrategy = (Long, Long) => Long

  // Method to compare strategies
  def compareStrategies(strategy1: LCMStrategy, strategy2: LCMStrategy, a: Long, b: Long): Boolean = {
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
    val maxRange = 1_000_000L // For example, up to 100 million
    val random = new Random()

    for (iteration <- 1 to maxIterations) {
      val a = Math.abs(random.nextLong()) % maxRange
      val b = Math.abs(random.nextLong()) % maxRange

      println(s"Testing with a = $a and b = $b")

      val strategies = Seq(
        lcm_naive1 _,
        lcm_naive2 _,
        lcm _,
        lcmFunctionalNaive _,
        lcmFunctional _
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
}

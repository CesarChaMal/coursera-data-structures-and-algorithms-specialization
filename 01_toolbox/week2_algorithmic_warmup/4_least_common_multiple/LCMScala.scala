import scala.annotation.tailrec
import scala.io.StdIn

object LCMScala {

  // O(a * b) time complexity - Naive Approach
  def lcm_naive(a: Long, b: Long): Long = {
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

  def main(args: Array[String]): Unit = {
    val inputs = StdIn.readLine().split(" ")
    val a = inputs(0).toLong
    val b = inputs(1).toLong

    // Choose the desired method to calculate LCM
    //    println(lcm_naive(a, b)) // Naive approach
    println(lcm(a, b)) // Efficient approach
    //    println(lcmFunctionalNaive(a, b)) // Functional Naive approach
    //    println(lcmFunctional(a, b)) // Functional approach
  }
}

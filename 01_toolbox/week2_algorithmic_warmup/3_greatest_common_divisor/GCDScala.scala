import scala.annotation.tailrec
import scala.io.StdIn

object GCDScala {

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

  def main(args: Array[String]): Unit = {
    val inputs = StdIn.readLine().split(" ")
    val a = inputs(0).toLong
    val b = inputs(1).toLong

    println(gcd(a, b))
  }

  // Read a single integer from standard input
  def readInt(): Int = StdIn.readInt()

  // Read a line of text from standard input
  def readLine(): String = StdIn.readLine()
}

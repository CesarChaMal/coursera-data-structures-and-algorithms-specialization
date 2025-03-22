import scala.annotation.tailrec
import scala.io.StdIn

object FibonacciHugeScala {

  // O(N) time complexity
  private def getFibonacciHugeNaive1(n: Long, m: Long): Long = {
    if (n <= 1) return n
    var previous = 0L
    var current = 1L
    for (_ <- 0L until n - 1) {
      val temp = previous
      previous = current
      current = (temp + current) % m
    }
    current
  }

  // O(N) time complexity
  private def getPisanoPeriod(m: Long): Long = {
    var previous = 0L
    var current = 1L
    var i = 0L
    while (i < m * m) {
      val temp = current
      current = (previous + current) % m
      previous = temp
      if (previous == 0 && current == 1) return i + 1
      i += 1
    }
    i
  }

  // O(N) time complexity
  private def getFibonacciHugeNaive2(n: Long, m: Long): Long = {
    val pisanoPeriod = getPisanoPeriod(m)
    val reducedN = n % pisanoPeriod
    var previous = 0L
    var current = 1L
    if (reducedN <= 1) return reducedN
    for (_ <- 0L until reducedN - 1) {
      val temp = current
      current = (previous + current) % m
      previous = temp
    }
    current
  }

  // O(N) time complexity
  private def getFibonacciHugeNaive3(n: Long, m: Long): Long = {
    if (n <= 1) return n

    var previous = 0L
    var current = 1L
    for (_ <- 0L until n - 1) {
      val next = (previous + current) % m
      previous = current
      current = next
    }
    current
  }

  private def matrixMultiply(a: Array[Array[Long]], b: Array[Array[Long]], m: Long): Array[Array[Long]] = {
    Array(
      Array((a(0)(0) * b(0)(0) + a(0)(1) * b(1)(0)) % m, (a(0)(0) * b(0)(1) + a(0)(1) * b(1)(1)) % m),
      Array((a(1)(0) * b(0)(0) + a(1)(1) * b(1)(0)) % m, (a(1)(0) * b(0)(1) + a(1)(1) * b(1)(1)) % m)
    )
  }

  private def matrixPower(matrix: Array[Array[Long]], n: Long, m: Long): Array[Array[Long]] = {
    if (n == 0) return Array(Array(1L, 0L), Array(0L, 1L))
    if (n == 1) return matrix
    val halfPower = matrixPower(matrix, n / 2, m)
    val square = matrixMultiply(halfPower, halfPower, m)
    if (n % 2 == 0) square else matrixMultiply(matrix, square, m)
  }

  // O(log N) time complexity
  private def getFibonacciHuge(n: Long, m: Long): Long = {
    if (n <= 1) return n
    val initialMatrix = Array(Array(1L, 1L), Array(1L, 0L))
    val resultMatrix = matrixPower(initialMatrix, n - 1, m)
    resultMatrix(0)(0)
  }

  // Helper method for functional style Fibonacci
  @tailrec
  private def fibHelper(n: Long, prev: Long, curr: Long, m: Long): Long = {
    if (n == 0) prev
    else fibHelper(n - 1, curr, (prev + curr) % m, m)
  }

  // O(N) time complexity, Functional programming style
  private def getFibonacciHugeNaive1Functional(n: Long, m: Long): Long =
    fibHelper(n, 0, 1, m)

  // O(N) time complexity, Functional programming style
  private def getFibonacciHugeNaive2Functional(n: Long, m: Long): Long = {
    val pisanoPeriod = getPisanoPeriod(m)
    fibHelper(n % pisanoPeriod, 0, 1, m)
  }

  private def matrixPowerFunctional(matrix: Array[Array[Long]], n: Long, m: Long): Array[Array[Long]] = {
    if (n == 0) return Array(Array(1L, 0L), Array(0L, 1L))
    if (n == 1) return matrix
    val halfPower = matrixPowerFunctional(matrix, n / 2, m)
    val square = matrixMultiply(halfPower, halfPower, m)
    if (n % 2 == 0) square else matrixMultiply(matrix, square, m)
  }

  // O(log N) time complexity - Functional style
  private def getFibonacciHugeFunctional(n: Long, m: Long): Long = {
    if (n <= 1) return n
    val initialMatrix = Array(Array(1L, 1L), Array(1L, 0L))
    val resultMatrix = matrixPowerFunctional(initialMatrix, n - 1, m)
    resultMatrix(0)(0)
  }

  def main(args: Array[String]): Unit = {
    val n = readLong()
    val m = readLong()
    println(getFibonacciHuge(n, m))
  }

  // Read a single integer from standard input
  def readInt(): Int = StdIn.readInt()

  // Read a single long from standard input
  def readLong(): Long = StdIn.readLong()

  // Read a line of text from standard input
  def readLine(): String = StdIn.readLine()
}

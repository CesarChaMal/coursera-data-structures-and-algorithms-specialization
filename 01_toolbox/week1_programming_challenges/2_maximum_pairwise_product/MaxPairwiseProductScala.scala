import scala.io.StdIn
import scala.collection.mutable.Queue
import scala.util.Random

object MaxPairwiseProductScala {

  // O(n^2) time complexity
  def getMaxPairwiseProductNaive(numbers: Array[Int]): Int = {
    var maxProduct = 0
    val n = numbers.length

    for (first <- 0 until n; second <- first + 1 until n) {
      maxProduct = math.max(maxProduct, numbers(first) * numbers(second))
    }

    maxProduct
  }

  // O(n^2) time complexity
  def getMaxPairwiseProduct(numbers: Array[Int]): Int = {
    if (numbers.length < 2) return 0 // or throw an error

    var max1 = math.max(numbers(0), numbers(1))
    var max2 = math.min(numbers(0), numbers(1))

    for (i <- 2 until numbers.length) {
      if (numbers(i) > max1) {
        max2 = max1
        max1 = numbers(i)
      } else if (numbers(i) > max2) {
        max2 = numbers(i)
      }
    }

    max1 * max2
  }

  // O(n log n) time complexity, functional approach
  def getMaxPairwiseProductFunctionalNaive(numbers: Array[Int]): Int = {
    numbers.sorted.reverse.take(2).product
  }

  // O(n) time complexity, functional approach
  def getMaxPairwiseProductFunctional(numbers: Array[Int]): Int = {
    val (max1, max2) = numbers.foldLeft((-1, -1)) {
      case ((first, second), current) =>
        if (current > first) (current, first)
        else if (current > second) (first, current)
        else (first, second)
    }
    max1 * max2
  }

  def main(args: Array[String]): Unit = {
    val n = readInt()
    val numbers = readLine().split(" ").map(_.toInt)
    println(getMaxPairwiseProduct(numbers))
  }

  // Read a single integer from standard input
  def readInt(): Int = StdIn.readInt()

  // Read a line of text from standard input
  def readLine(): String = StdIn.readLine()
}

import scala.io.StdIn
import scala.collection.mutable.Queue
import scala.util.Random

object MaxPairwiseProductScalaStressTest {

  // O(n^2) time complexity
  def getMaxPairwiseProductNaive(numbers: Array[Long]): Long = {
    var maxProduct: Long = 0
    val n = numbers.length

    for (first <- 0 until n; second <- first + 1 until n) {
      maxProduct = math.max(maxProduct, numbers(first) * numbers(second))
    }

    maxProduct
  }

  // O(n) time complexity
  def getMaxPairwiseProduct(numbers: Array[Long]): Long = {
    if (numbers.length < 2) return 0 // or throw an error

    var max1: Long = math.max(numbers(0), numbers(1))
    var max2: Long = math.min(numbers(0), numbers(1))

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
  def getMaxPairwiseProductFunctionalNaive(numbers: Array[Long]): Long = {
    numbers.sorted.reverse.take(2).product
  }

  // O(n) time complexity, functional approach
  def getMaxPairwiseProductFunctional(numbers: Array[Long]): Long = {
    val (max1, max2) = numbers.foldLeft((-1L, -1L)) {
      case ((first, second), current) =>
        if (current > first) (current, first)
        else if (current > second) (first, current)
        else (first, second)
    }
    max1 * max2
  }

  def stressTest(maxIterations: Int = 100000): Unit = {
    val random = new Random()

    for (iteration <- 1 to maxIterations) {
      val n = random.nextInt(1000) + 2
      val numbers = Array.fill(n)(random.nextLong().abs % 100000)

      println(s"Iteration $iteration: Array size - $n")
      println("Array: " + numbers.mkString(" "))

      val resultNaive = getMaxPairwiseProductNaive(numbers)
      val resultEfficient = getMaxPairwiseProduct(numbers)
      val resultFunctionalNaive = getMaxPairwiseProductFunctionalNaive(numbers)
      val resultFunctional = getMaxPairwiseProductFunctional(numbers)

      if (resultNaive != resultEfficient || resultFunctional != resultEfficient || resultFunctionalNaive != resultEfficient) {
        println("Discrepancy found:")
        if (resultNaive != resultEfficient) println(s"Naive vs Efficient: $resultNaive != $resultEfficient")
        if (resultFunctional != resultEfficient) println(s"Functional vs Efficient: $resultFunctional != $resultEfficient")
        if (resultFunctionalNaive != resultEfficient) println(s"Functional Naive vs Efficient: $resultFunctionalNaive != $resultEfficient")
        return
      } else {
        println("OK")
      }
    }
  }

  def main(args: Array[String]): Unit = {
    // Uncomment to run the stress test
    stressTest()

    // Uncomment to run with user input
    // val n = readInt()
    // val numbers = readLine().split(" ").map(_.toLong)
    // println(getMaxPairwiseProduct(numbers))
  }
}

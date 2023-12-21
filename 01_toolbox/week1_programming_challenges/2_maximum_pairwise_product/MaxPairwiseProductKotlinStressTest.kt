import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
//import kotlin.math.maxOf
//import kotlin.math.minOf
import kotlin.random.Random
import kotlin.random.nextLong

class MaxPairwiseProductKotlinStressTest {

    // O(n^2) time complexity
    fun getMaxPairwiseProductNaive(numbers: LongArray): Long {
        var result: Long = 0
        val n = numbers.size
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                if (numbers[i] * numbers[j] > result) {
                    result = numbers[i] * numbers[j]
                }
            }
        }
        return result
    }

    // O(n) time complexity
    fun getMaxPairwiseProduct(numbers: LongArray): Long {
        if (numbers.size < 2) return 0

//    var max1 = maxOf(numbers[0], numbers[1])
//    var max2 = minOf(numbers[0], numbers[1])

        var max1 = Math.max(numbers[0], numbers[1])
        var max2 = Math.min(numbers[0], numbers[1])

        for (i in 2 until numbers.size) {
            when {
                numbers[i] > max1 -> {
                    max2 = max1
                    max1 = numbers[i]
                }
                numbers[i] > max2 -> max2 = numbers[i]
            }
        }

        return max1 * max2
    }

    // O(n log n) time complexity, functional approach
    fun getMaxPairwiseProductFunctionalNaive(numbers: LongArray): Long {
        return numbers.sortedDescending().take(2).reduce(Long::times)
    }

    // O(n) time complexity, functional approach
    fun getMaxPairwiseProductFunctional(numbers: LongArray): Long {
        val (max1, max2) = numbers.fold(-1L to -1L) { (first, second), current ->
            when {
                current > first -> current to first
                current > second -> first to current
                else -> first to second
            }
        }
        return max1 * max2
    }

    fun compareStrategies(numbers: LongArray, strategy1: (LongArray) -> Long, strategy2: (LongArray) -> Long, description: String): Boolean {
        val result1 = strategy1(numbers)
        val result2 = strategy2(numbers)
        if (result1 != result2) {
            println("Discrepancy found in $description: $result1 != $result2")
            return false
        }
        return true
    }

    fun stressTest(maxIterations: Int = 100000, arraySize: Int = 1000, maxValue: Long = 100000) {
        for (iteration in 1..maxIterations) {
            val numbers = LongArray(Random.nextInt(2, arraySize)) { Random.nextLong(0, maxValue) }
            println("Iteration $iteration: Array size - ${numbers.size}")
            println("Array: ${numbers.joinToString(" ")}")

            /*
                    val resultNaive = getMaxPairwiseProductNaive(numbers)
                    val resultEfficient = getMaxPairwiseProduct(numbers)
                    val resultFunctionalNaive = getMaxPairwiseProductFunctionalNaive(numbers)
                    val resultFunctional = getMaxPairwiseProductFunctional(numbers)

                    if (resultNaive != resultEfficient || resultFunctionalNaive != resultEfficient || resultFunctional != resultEfficient) {
                        println("Discrepancy found:")
                        println("Naive: $resultNaive")
                        println("Efficient: $resultEfficient")
                        println("Functional Naive: $resultFunctionalNaive")
                        println("Functional: $resultFunctional")
                        return
                    } else {
                        println("All methods agree.")
                    }
            */

            val strategiesToCompare = listOf(
                ::getMaxPairwiseProductNaive to "Naive",
                ::getMaxPairwiseProductFunctional to "Functional",
                ::getMaxPairwiseProductFunctionalNaive to "Functional Naive"
            )

            val efficientResult = { numbers: LongArray -> getMaxPairwiseProduct(numbers) }
            for ((strategy, description) in strategiesToCompare) {
                if (!compareStrategies(numbers, strategy, efficientResult, description)) {
                    return
                }
            }

            println("All methods agree.")
        }
        println("All tests passed successfully.")
    }

    class FastScanner(stream: InputStream) {
        var br: BufferedReader = BufferedReader(InputStreamReader(stream))
        var st: StringTokenizer? = null

        fun next(): String {
            while (st == null || !st!!.hasMoreTokens()) {
                try {
                    st = StringTokenizer(br.readLine())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return st!!.nextToken()
        }

        fun nextInt() = next().toInt()
        fun nextLong() = next().toLong()
    }

}

fun main(args: Array<String>) {
    val tester = MaxPairwiseProductKotlinStressTest()
    tester.stressTest() // Run the stress test

    // If you want to also run with user input, you can uncomment the following lines
    /*
    val scanner = MaxPairwiseProductKotlin.FastScanner(System.`in`)
    val n = scanner.nextInt()
    val numbers = IntArray(n)
    for (i in 0 until n) {
        numbers[i] = scanner.nextInt()
    }

    val result = MaxPairwiseProductKotlinStressTest()
    println(result.getMaxPairwiseProduct(numbers))
    */
}


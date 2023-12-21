import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class MaxPairwiseProductKotlin {

    // O(n^2) time complexity
    fun getMaxPairwiseProductNaive(numbers: IntArray): Int {
        var result = 0
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
    fun getMaxPairwiseProduct(numbers: IntArray): Int {
        if (numbers.size < 2) return 0

        var max1 = maxOf(numbers[0], numbers[1])
        var max2 = minOf(numbers[0], numbers[1])

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
    fun getMaxPairwiseProductFunctionalNaive(numbers: IntArray): Int {
        return numbers.sortedDescending().take(2).reduce(Int::times)
    }

    // O(n) time complexity, functional approach
    fun getMaxPairwiseProductFunctional(numbers: IntArray): Int {
        val (max1, max2) = numbers.fold(-1 to -1) { (first, second), current ->
            when {
                current > first -> current to first
                current > second -> first to current
                else -> first to second
            }
        }
        return max1 * max2
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
    val scanner = MaxPairwiseProductKotlin.FastScanner(System.`in`)
    val n = scanner.nextInt()
    val numbers = IntArray(n)
    for (i in 0 until n) {
        numbers[i] = scanner.nextInt()
    }

    val result = MaxPairwiseProductKotlin()
    println(result.getMaxPairwiseProduct(numbers))
}

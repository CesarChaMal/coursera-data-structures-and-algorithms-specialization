import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class FibonacciLastDigitKotlinStressTest {

    // O(n) time complexity
    fun getFibonacciLastDigitNaive(n: Long): Long {
        if (n <= 1) return n

        var prev = 0L
        var current = 1L
        for (i in 2..n) {
            val tmp_previous = prev
            prev = current
            current = tmp_previous + current
        }
        return current % 10
    }

    // O(n) time complexity
    fun getFibonacciLastDigit(n: Long): Long {
        if (n <= 1) return n

        var prev = 0L
        var current = 1L
        for (i in 2..n) {
            val tmp_previous = prev
            prev = current
            current = (tmp_previous + current) % 10
        }
        return current
    }

    // O(n) time complexity, functional approach
    fun getFibonacciLastDigitFunctionalNaive(n: Long): Long {
        return generateSequence(Pair(0L, 1L)) { Pair(it.second, it.first + it.second) }
            .map { it.first }
            .take(n.toInt() + 1)
            .last() % 10
    }

    // O(n) time complexity, functional approach
    fun getFibonacciLastDigitFunctional(n: Long): Long {
        return generateSequence(Pair(0L, 1L)) { Pair(it.second, (it.first + it.second) % 10) }
            .map { it.first }
            .take(n.toInt() + 1)
            .last()
    }

    // Functional interface for strategies
    fun interface FibonacciStrategy {
        fun compute(n: Long): Long
    }

    // Method to compare strategies
    fun compareStrategies(n: Long, strategy1: FibonacciStrategy, strategy2: FibonacciStrategy): Boolean {
        val result1 = strategy1.compute(n)
        val result2 = strategy2.compute(n)
        if (result1 != result2) {
            println("Discrepancy found: $result1 != $result2")
            return false
        }
        return true
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

        fun nextLong() = next().toLong()
    }
}

fun main(args: Array<String>) {
    // Stress test
    val tester = FibonacciLastDigitKotlinStressTest()
    val maxIterations = 100_000
    val random = Random()

    for (iteration in 1..maxIterations) {
        val n = random.nextInt(40).toLong() // Limit n to a reasonable range for the naive method

        println("Testing with n = $n")

        val naive = FibonacciLastDigitKotlinStressTest.FibonacciStrategy { tester.getFibonacciLastDigitNaive(it) }
        val efficient = FibonacciLastDigitKotlinStressTest.FibonacciStrategy { tester.getFibonacciLastDigit(it) }
        val functional = FibonacciLastDigitKotlinStressTest.FibonacciStrategy { tester.getFibonacciLastDigitFunctionalNaive(it) }
        val functionalNaive = FibonacciLastDigitKotlinStressTest.FibonacciStrategy { tester.getFibonacciLastDigitFunctional(it) }

        if (!tester.compareStrategies(n, naive, efficient)) {
            println("Naive vs Efficient failed")
            break
        }
        if (!tester.compareStrategies(n, functionalNaive, efficient)) {
            println("Functional Naive vs Efficient failed")
            break
        }
        if (!tester.compareStrategies(n, functional, efficient)) {
            println("Functional vs Efficient failed")
            break
        }

        println("OK")
    }

    // Uncomment to run with user input
    // val scanner = FibonacciLastDigitKotlinStressTest.FastScanner(System.`in`)
    // val n = scanner.nextLong()
    // println(FibonacciLastDigitKotlinStressTest().calcFib(n))
}

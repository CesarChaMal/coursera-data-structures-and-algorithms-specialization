import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class FibonacciKotlinStressTest {

    // O(2^n) time complexity
    fun calcFibNaive(n: Long): Long {
        return if (n <= 1) n else calcFibNaive(n - 1) + calcFibNaive(n - 2)
    }

    // O(n) time complexity
    fun calcFib(n: Long): Long {
        if (n <= 1) return n

        var prev = 0L
        var current = 1L
        for (i in 2..n) {
            val newCurrent = prev + current
            prev = current
            current = newCurrent
        }
        return current
    }


    // O(2^n) time complexity, functional naive approach
    fun calcFibFunctionalNaive(n: Long): Long {
        tailrec fun fibHelper(n: Long, a: Long = 0, b: Long = 1): Long {
            return if (n == 0L) a else fibHelper(n - 1, b, a + b)
        }
        return fibHelper(n)
    }

    // O(n) time complexity, functional approach
    fun calcFibFunctional(n: Long): Long {
        return generateSequence(Pair(0L, 1L)) { Pair(it.second, it.first + it.second) }
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
    val tester = FibonacciKotlinStressTest()
    val maxIterations = 100_000
    val random = Random()

    for (iteration in 1..maxIterations) {
        val n = random.nextInt(40).toLong() // Limit n to a reasonable range for the naive method

        println("Testing with n = $n")

        val naive = FibonacciKotlinStressTest.FibonacciStrategy { tester.calcFibNaive(it) }
        val efficient = FibonacciKotlinStressTest.FibonacciStrategy { tester.calcFib(it) }
        val functional = FibonacciKotlinStressTest.FibonacciStrategy { tester.calcFibFunctional(it) }
        val functionalNaive = FibonacciKotlinStressTest.FibonacciStrategy { tester.calcFibFunctionalNaive(it) }

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
    // val scanner = FibonacciKotlinStressTest.FastScanner(System.`in`)
    // val n = scanner.nextLong()
    // println(FibonacciKotlinStressTest().calcFib(n))
}

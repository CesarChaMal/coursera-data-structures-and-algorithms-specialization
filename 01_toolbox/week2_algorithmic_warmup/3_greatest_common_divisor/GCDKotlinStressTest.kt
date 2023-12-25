import GCDJavaStressTest.GCDStrategy
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.math.abs

class GCDKotlinStressTest {

    // O(min(a, b)) time complexity
    fun gcdNaive(a: Long, b: Long): Long {
        var currentGcd: Long = 1
        var d: Long = 2
        while (d <= a && d <= b) {
            if (a % d == 0L && b % d == 0L) {
                if (d > currentGcd) {
                    currentGcd = d
                }
            }
            ++d
        }

        return currentGcd
    }

    // O(log(min(a, b))) time complexityGCDJava
    // Euclidean Algorithm
    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    // O(min(a, b)) time complexity
    fun gcdFunctionalNaive(a: Long, b: Long): Long {
        tailrec fun gcdHelper(currentGcd: Long, d: Long): Long {
            if (d > Math.min(a, b)) return currentGcd
            val newGcd = if (a % d == 0L && b % d == 0L) d else currentGcd
            return gcdHelper(newGcd, d + 1)
        }

        return gcdHelper(1, 2)
    }

    // O(log(min(a, b))) time complexity
    // Euclidean Algorithm
    fun gcdFunctional(a: Long, b: Long): Long {
        tailrec fun euclidGcd(x: Long, y: Long): Long {
            return if (y == 0L) x else euclidGcd(y, x % y)
        }

        return euclidGcd(a, b)
    }

    // Functional interface for strategies
    fun interface GCDStrategy {
        fun compute(a: Long, b: Long): Long
    }

    // Method to compare strategies
    fun compareStrategies(strategy1: GCDStrategy, strategy2: GCDStrategy, a: Long, b: Long): Boolean {
        val result1 = strategy1.compute(a, b)
        val result2 = strategy2.compute(a, b)
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
    val tester = GCDKotlinStressTest()
    val maxIterations = 100_000
    val maxRange = 100_000_000L // For example, up to 100 million
    val random = Random()

    for (iteration in 1..maxIterations) {
        val a = abs(random.nextLong()) % maxRange
        val b = abs(random.nextLong()) % maxRange

        println("Testing with a = $a and b = $b")

        val naive = GCDKotlinStressTest.GCDStrategy { x, y -> tester.gcdNaive(x, y) }
        val efficient = GCDKotlinStressTest.GCDStrategy { x, y -> tester.gcd(x, y) }
        val functionalNaive = GCDKotlinStressTest.GCDStrategy { x, y -> tester.gcdFunctionalNaive(x, y) }
        val functional = GCDKotlinStressTest.GCDStrategy { x, y -> tester.gcdFunctional(x, y) }

        if (!tester.compareStrategies(naive, efficient, a, b)) {
            println("Naive vs Efficient failed")
            break
        }
        if (!tester.compareStrategies(functionalNaive, efficient, a, b)) {
            println("Functional Naive vs Efficient failed")
            break
        }
        if (!tester.compareStrategies(functional, efficient, a, b)) {
            println("Functional vs Efficient failed")
            break
        }

        println("OK")
    }

//    val scanner = GCDKotlin.FastScanner(System.`in`)
////    val scanner = Scanner(System.`in`)
//    val a = scanner.nextLong()
//    val b = scanner.nextLong()
//
//    val result = GCDKotlin()
//
//    println(result.gcdNaive(a, b))
}

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import java.util.function.BiFunction

class LCMKotlin {

    // O(a * b) time complexity - Naive LCM
    fun lcmNaive1(a: Long, b: Long): Long {
        for (l in 1..a.toLong() * b)
            if (l % a == 0L && l % b == 0L)
                return l

        return a * b
    }

    // O(a * b) time complexity - Alternative Naive LCM
    fun lcmNaive2(a: Long, b: Long): Long {
        var lcm = 1L
        while (true) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm++
        }
    }

    // O(log(min(a, b))) time complexity - Efficient GCD
    fun gcd(a: Long, b: Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

    // O(log(min(a, b))) time complexity - Efficient LCM
    fun lcm(a: Long, b: Long): Long {
        return (a * b) / gcd(a, b)
    }

    // O(a * b) time complexity - Functional Naive LCM
    fun lcmFunctionalNaive(a: Long, b: Long): Long {
        val limit = a * b
        for (current in 1L..limit) {
            if (current % a == 0L && current % b == 0L) {
                return current
            }
        }
        return limit
    }

    // O(a * b) time complexity - Functional Naive LCM
    val lcmFunctionalNaive: BiFunction<Long, Long, Long> = BiFunction { a, b ->
        val limit = a.toLong() * b
        for (current in 1L..limit) {
            if (current % a == 0L && current % b == 0L) {
                return@BiFunction current
            }
        }
        limit
    }

    // O(log(min(a, b))) time complexity - Functional GCD
    private fun gcdFunctional(a: Long, b: Long): Long {
        return if (b == 0L) a else gcdFunctional(b, a % b)
    }

    // O(log(min(a, b))) time complexity - Efficient LCM using Functional GCD
    fun lcmFunctional(a: Long, b: Long): Long {
        return (a * b) / gcdFunctional(a, b)
    }

    // O(log(min(a, b))) time complexity - Efficient LCM using Functional GCD
    val lcmFunctional: BiFunction<Long, Long, Long> = BiFunction { a, b ->
        (a * b) / gcdFunctional(a, b)
    }

    // Functional interface for strategies
    fun interface LCMStrategy {
        fun compute(a: Long, b: Long): Long
    }

    // Method to compare strategies
    fun compareStrategies(strategy1: LCMStrategy, strategy2: LCMStrategy, a: Long, b: Long): Boolean {
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
    val scanner = LCMKotlin.FastScanner(System.`in`)
//    val scanner = Scanner(System.`in`)
    val a = scanner.nextLong()
    val b = scanner.nextLong()

    val result = LCMKotlin()

    println(result.gcd(a, b))
}

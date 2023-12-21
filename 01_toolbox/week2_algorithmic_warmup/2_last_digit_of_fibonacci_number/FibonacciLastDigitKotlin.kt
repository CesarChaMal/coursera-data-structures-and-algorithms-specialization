import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class FibonacciLastDigitKotlin {

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
    val scanner = FibonacciLastDigitKotlin.FastScanner(System.`in`)
    val n = scanner.nextLong()

    val result = FibonacciLastDigitKotlin()

    println(result.getFibonacciLastDigit(n))
}

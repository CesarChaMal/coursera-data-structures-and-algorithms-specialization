import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class FibonacciKotlin {

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
    val scanner = FibonacciKotlin.FastScanner(System.`in`)
    val n = scanner.nextLong()

    val result = FibonacciKotlin()

    println(result.calcFib(n))
}

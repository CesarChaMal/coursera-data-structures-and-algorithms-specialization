import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class FibonacciHugeKotlin {

    // O(n) time complexity
    fun getFibonacciHugeNaive1(n: Long, m: Long): Long {
        if (n <= 1) return n

        var previous: Long = 0
        var current: Long = 1

        for (i in 0 until n - 1) {
            val tmpPrevious = previous
            previous = current
            current += tmpPrevious
        }

        return current % m
    }

    // O(N) time complexity
    fun getPisanoPeriod(m: Long): Long {
        var previous: Long = 0
        var current: Long = 1
        var res: Long = 0

        for (i in 0 until m * m) {
            val temp = current
            current = (previous + current) % m
            previous = temp

            if (previous == 0L && current == 1L) {
                res = i + 1
                break
            }
        }
        return res
    }

    // O(N) time complexity
    fun getFibonacciHugeNaive2(n: Long, m: Long): Long {
        val pisanoPeriod = getPisanoPeriod(m)
        val reducedN = n % pisanoPeriod

        var previous: Long = 0
        var current: Long = 1

        if (reducedN <= 1) return reducedN

        for (i in 0 until reducedN - 1) {
            val temp = current
            current = (previous + current) % m
            previous = temp
        }

        return current
    }

    // O(N) time complexity
    fun getFibonacciHugeNaive3(n: Long, m: Long): Long {
        if (n <= 1) return n

        var previous: Long = 0
        var current: Long = 1

        for (i in 0 until n - 1) {
            val next = (previous + current) % m
            previous = current
            current = next
        }

        return current
    }

    private fun matrixPower(matrix: Array<LongArray>, n: Long, m: Long): Array<LongArray> {
        if (n == 0L || n == 1L) return matrix
        val temp = matrixPower(matrix, n / 2, m)
        val sq = matrixMultiply(temp, temp, m)
        return if (n % 2 == 0L) sq else matrixMultiply(matrix, sq, m)
    }

    private fun matrixMultiply(a: Array<LongArray>, b: Array<LongArray>, m: Long): Array<LongArray> {
        return arrayOf(
            longArrayOf((a[0][0] * b[0][0] + a[0][1] * b[1][0]) % m, (a[0][0] * b[0][1] + a[0][1] * b[1][1]) % m),
            longArrayOf((a[1][0] * b[0][0] + a[1][1] * b[1][0]) % m, (a[1][0] * b[0][1] + a[1][1] * b[1][1]) % m)
        )
    }

    // O(log N) time complexity
    fun getFibonacciHuge(n: Long, m: Long): Long {
        if (n <= 1) return n

        val initialMatrix = arrayOf(longArrayOf(1, 1), longArrayOf(1, 0))
        val resultMatrix = matrixPower(initialMatrix, n - 1, m)
        return resultMatrix[0][0]
    }

    // Helper method for functional style Fibonacci
    private fun fibHelper(n: Long, prev: Long, curr: Long, m: Long): Long {
        if (n == 0L) return prev
        return fibHelper(n - 1L, curr, (prev + curr) % m, m)
    }

    // O(N) time complexity, Functional programming style
    fun getFibonacciHugeNaive1Functional(n: Long, m: Long): Long {
        return fibHelper(n, 0, 1, m)
    }

    // O(N) time complexity, Functional programming style
    fun getFibonacciHugeNaive2Functional(n: Long, m: Long): Long {
        val pisanoPeriod = getPisanoPeriod(m)
        return fibHelper(n % pisanoPeriod, 0, 1, m)
    }

    // Helper method for matrix power in functional style
    private fun matrixPowerFunctional(matrix: Array<LongArray>, n: Long, m: Long): Array<LongArray> {
        if (n == 0L) return arrayOf(longArrayOf(1, 0), longArrayOf(0, 1))
        if (n == 1L) return matrix

        val halfPower = matrixPowerFunctional(matrix, n / 2, m)
        val square = matrixMultiply(halfPower, halfPower, m)

        return if (n % 2L == 0L) square else matrixMultiply(matrix, square, m)
    }

    // O(log N) time complexity, Functional programming style
    fun getFibonacciHugeFunctional(n: Long, m: Long): Long {
        if (n <= 1) return n

        val initialMatrix = arrayOf(longArrayOf(1, 1), longArrayOf(1, 0))
        val resultMatrix = matrixPowerFunctional(initialMatrix, n - 1, m)
        return resultMatrix[0][0]
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
    val scanner = FibonacciHugeKotlin.FastScanner(System.`in`)
    val n = scanner.nextLong()
    val m = scanner.nextLong()

    val result = FibonacciHugeKotlin()

    println(result.getFibonacciHugeNaive1(n, m))
}

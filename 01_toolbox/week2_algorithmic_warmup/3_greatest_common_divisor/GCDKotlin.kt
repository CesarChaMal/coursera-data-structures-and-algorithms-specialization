import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class GCDKotlin {

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
    val scanner = GCDKotlin.FastScanner(System.`in`)
//    val scanner = Scanner(System.`in`)
    val a = scanner.nextLong()
    val b = scanner.nextLong()

    val result = GCDKotlin()

    println(result.gcd(a, b))
}

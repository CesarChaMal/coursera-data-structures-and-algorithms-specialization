import java.util.Random;

import static java.lang.System.out;

public class FibonacciHugeJavaStressTest {

    // O(N) time complexity
    private static long getFibonacciHugeNaive1(long n, long m) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;

        for (long i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
        }

        return current % m;
    }

    // O(N) time complexity
    private static long getPisanoPeriod(long m) {
        long previous = 0;
        long current = 1;
        long res = 0;

        for (int i = 0; i < m * m; i++) {
            long temp = current;
            current = (previous + current) % m;
            previous = temp;

            if (previous == 0 && current == 1) {
                res = i + 1;
                break;
            }
        }
        return res;
    }

    // O(N) time complexity
    private static long getFibonacciHugeNaive2(long n, long m) {
        long pisanoPeriod = getPisanoPeriod(m);

        n = n % pisanoPeriod;

        long previous = 0;
        long current = 1;

        if (n <= 1)
            return n;

        for (int i = 0; i < n - 1; i++) {
            long temp = current;
            current = (previous + current) % m;
            previous = temp;
        }

        return current % m;
    }

    // O(N) time complexity
    private static long getFibonacciHugeNaive3(long n, long m) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current = 1;
        for (long i = 0; i < n - 1; ++i) {
            long next = (previous + current) % m;
            previous = current;
            current = next;
        }
        return current;
    }

    private static long[][] multiplyMatrices(long[][] firstMatrix, long[][] secondMatrix, long m) {
        long[][] result = new long[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 2; k++) {
                    result[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                    result[i][j] %= m;
                }
            }
        }
        return result;
    }

    private static long[][] matrixPower(long[][] matrix, long n, long m) {
        if (n == 0 || n == 1)
            return matrix;
        long[][] temp = matrixPower(matrix, n / 2, m);
        long[][] sq = multiplyMatrices(temp, temp, m);
        if (n % 2 == 0)
            return sq;
        else
            return multiplyMatrices(matrix, sq, m);
    }

    // O(log N) time complexity
    private static long getFibonacciHuge(long n, long m) {
        if (n <= 1)
            return n;

        long[][] initialMatrix = {{1, 1}, {1, 0}};
        long[][] resultMatrix = matrixPower(initialMatrix, n - 1, m);
        return resultMatrix[0][0];
    }

    // Helper method for functional style Fibonacci
    private static long fibHelper(long n, long prev, long curr, long m) {
        if (n == 0) return prev;
        return fibHelper(n - 1, curr, (prev + curr) % m, m);
    }

    // O(N) time complexity, Functional programming style
    private static long getFibonacciHugeNaive1Functional(long n, long m) {
        return fibHelper(n, 0, 1, m);
    }

    // O(N) time complexity, Functional programming style
    private static long getFibonacciHugeNaive2Functional(long n, long m) {
        long pisanoPeriod = getPisanoPeriod(m);
        n = n % pisanoPeriod;
        return fibHelper(n, 0, 1, m);
    }

    private static long[][] matrixMultiply(long[][] a, long[][] b, long m) {
        return new long[][]{
                {(a[0][0] * b[0][0] + a[0][1] * b[1][0]) % m, (a[0][0] * b[0][1] + a[0][1] * b[1][1]) % m},
                {(a[1][0] * b[0][0] + a[1][1] * b[1][0]) % m, (a[1][0] * b[0][1] + a[1][1] * b[1][1]) % m}
        };
    }

    private static long[][] matrixPowerFunctional(long[][] matrix, long n, long m) {
        if (n == 0) return new long[][]{{1, 0}, {0, 1}};
        if (n == 1) return matrix;

        long[][] halfPower = matrixPowerFunctional(matrix, n / 2, m);
        long[][] square = matrixMultiply(halfPower, halfPower, m);

        if (n % 2 == 0) {
            return square;
        } else {
            return matrixMultiply(matrix, square, m);
        }
    }

    // O(log N) time complexity - Functional style
    private static long getFibonacciHugeFunctional(long n, long m) {
        if (n <= 1) return n;

        long[][] initialMatrix = {{1, 1}, {1, 0}};
        long[][] resultMatrix = matrixPowerFunctional(initialMatrix, n - 1, m);
        return resultMatrix[0][0];
    }

    // Functional interface for strategies
    @FunctionalInterface
    interface FibonacciHugeStrategy {
        long compute(long n, long m);
    }

    // Method to compare strategies
    static boolean compareStrategies(FibonacciHugeJavaStressTest.FibonacciHugeStrategy strategy1, FibonacciHugeJavaStressTest.FibonacciHugeStrategy strategy2, long n, long m) {
        long result1 = strategy1.compute(n, m);
        long result2 = strategy2.compute(n, m);
        if (result1 != result2) {
            out.printf("Discrepancy found: %d != %d%n", result1, result2);
            return false;
        }
        return true;
    }

    public static void main(String args[]) {
        // Stress test
        int maxIterations = 100_000;
        Random random = new Random();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            long n = random.nextInt(40); // Limit n to a reasonable range for the naive method
            long m = random.nextInt(100) + 2; // m in range [2, 101]

            out.println("Testing with n = " + n + " and m = " + m);

            FibonacciHugeStrategy naive1 = FibonacciHugeJavaStressTest::getFibonacciHugeNaive1;
            FibonacciHugeStrategy naive2 = FibonacciHugeJavaStressTest::getFibonacciHugeNaive2;
            FibonacciHugeStrategy naive3 = FibonacciHugeJavaStressTest::getFibonacciHugeNaive3;
            FibonacciHugeStrategy efficient = FibonacciHugeJavaStressTest::getFibonacciHuge;
            FibonacciHugeStrategy functionalNaive1 = FibonacciHugeJavaStressTest::getFibonacciHugeNaive1Functional;
            FibonacciHugeStrategy functionalNaive2 = FibonacciHugeJavaStressTest::getFibonacciHugeNaive2Functional;
            FibonacciHugeStrategy functional = FibonacciHugeJavaStressTest::getFibonacciHugeFunctional;

            if (!compareStrategies(naive1, efficient, n, m) ||
                    !compareStrategies(naive2, efficient, n, m) ||
                    !compareStrategies(naive3, efficient, n, m) ||
                    !compareStrategies(functionalNaive1, efficient, n, m) ||
                    !compareStrategies(functionalNaive2, efficient, n, m) ||
                    !compareStrategies(functional, efficient, n, m)) {
                out.println("Mismatch found in iteration " + iteration);
                break;
            }

            out.println("OK");
        }
    }
}

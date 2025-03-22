import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.LongStream;

import static java.lang.System.*;
import static java.lang.System.out;

public class FibonacciSumLastDigitJavaStressTest {

    // O(n) time complexity
    public static long getFibonacciSumNaive(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current  = 1;
        long sum      = 1;

        for (long i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = (tmp_previous + current) % 10; // Modulo 10 here
            sum = (sum + current) % 10; // Modulo 10 here as well

            out.println("i=" + i + ", current=" + current + ", sum=" + sum);
        }

        return sum;
    }

    // O(log n) time complexity using Pisano Period
    public static long getFibonacciSumEfficient(long n) {
        if (n <= 1)
            return n;

        n = n % 60; // Pisano Period for modulo 10 is 60
        if (n == 0) return 0;
        if (n == 1) return 1;

        long previous = 0;
        long current = 1;
        long sum = 1;

        for (int i = 2; i <= n; i++) {
            long tmp_previous = previous;
            previous = current;
            current = (tmp_previous + current) % 10;
            sum = (sum + current) % 10;
        }

        return sum;
    }

    private static void multiply(long[][] F, long[][] M) {
        long x = (F[0][0] * M[0][0] + F[0][1] * M[1][0]) % 10;
        long y = (F[0][0] * M[0][1] + F[0][1] * M[1][1]) % 10;
        long z = (F[1][0] * M[0][0] + F[1][1] * M[1][0]) % 10;
        long w = (F[1][0] * M[0][1] + F[1][1] * M[1][1]) % 10;

        F[0][0] = x;
        F[0][1] = y;
        F[1][0] = z;
        F[1][1] = w;
    }

    private static void power(long[][] F, long n) {
        if (n == 0 || n == 1)
            return;
        long[][] M = {{1, 1}, {1, 0}};

        power(F, n / 2);
        multiply(F, F);

        if (n % 2 != 0)
            multiply(F, M);
    }

    // O(log n) time complexity using Matrix Exponentiation
    public static long getFibonacciSumMatrix(long n) {
        if (n <= 1)
            return n;

        long[][] F = {{1, 1}, {1, 0}};
        power(F, n + 1);

        // Sum of Fibonacci numbers is F[0][0] - 1
        // Adding 10 to handle negative values
        long matrixResult = (F[0][0] - 1 + 10) % 10;
        out.println("Matrix sum for n=37: " + matrixResult);

        return matrixResult;
    }

    // Helper method for getFibonacciSumNaiveFunctional
    public static long getFibonacciSumNaiveHelper(long n, long prev, long curr, long sum) {
        if (n == 0) {
            // Base case: when n is 0, return the sum accumulated so far
            return sum;
        }
        // Recursive call with n decremented, and sum updated
        return getFibonacciSumNaiveHelper(n - 1, curr, (prev + curr) % 10, (sum + (prev + curr) % 10) % 10);
    }

    // Functional equivalent of getFibonacciSumNaive
    public static long getFibonacciSumNaiveFunctional(long n) {
        if (n <= 1) {
            return n;
        }
        // Initialize with the first Fibonacci numbers and start with a sum of 1
        return getFibonacciSumNaiveHelper(n - 1, 0, 1, 1);
    }

    // Functional equivalent of getFibonacciSumEfficient
    public static long getFibonacciSumEfficientFunctional(long n) {
        if (n <= 1) return n;

        n = (n + 2) % 60; // Correct Pisano period adjustment

        long[][] F = {{1, 1}, {1, 0}};
        long[][] result = matrixPowerFunctional(F, n);

        return (result[0][0] - 1 + 10) % 10; // Correct sum calculation
    }

    // Functional equivalent of matrixMultiply
    private static long[][] matrixMultiplyFunctional(long[][] a, long[][] b) {
      BiFunction<Integer, Integer, Long> computeElement =
          (i, j) -> {
            long sum = 0;
            for (int k = 0; k < 2; k++) {
              sum = (sum + a[i][k] * b[k][j]) % 10;
            }
            return Long.valueOf(sum);
          };
      return new long[][]{
          {computeElement.apply(0, 0), computeElement.apply(0, 1)},
          {computeElement.apply(1, 0), computeElement.apply(1, 1)}
      };

    }

    // Functional equivalent of matrixPower
// Functional equivalent of matrixPower
    private static long[][] matrixPowerFunctional(long[][] matrix, long n) {
        if (n == 0)
            return new long[][]{{1, 0}, {0, 1}};
        if (n == 1)
            return matrix;

        // Compute half power of the matrix
        long[][] halfPower = matrixPowerFunctional(matrix, n / 2);
        // Square the half power
        long[][] square = matrixMultiplyFunctional(halfPower, halfPower);

        // If n is odd, multiply once more with the original matrix
        if (n % 2 != 0) {
            square = matrixMultiplyFunctional(square, matrix);
        }
        return square;
    }

    // O(log n) time complexity using Matrix Exponentiation
    public static long getFibonacciSumMatrixFunctional(long n) {
        if (n <= 1)
            return n;
        long[][] F = {{1, 1}, {1, 0}};
        return (matrixPowerFunctional(F, n + 1)[0][0] - 1 + 10) % 10; // Adding 10 to handle negative values
    }

    // Functional interface for strategies
    @FunctionalInterface
    interface FibonacciSumStrategy {
        long compute(long n);
    }

    // Method to compare strategies
    static boolean compareStrategies(FibonacciSumStrategy strategy1, FibonacciSumStrategy strategy2, long n) {
        long result1 = strategy1.compute(n);
        long result2 = strategy2.compute(n);
        if (result1 != result2) {
            out.printf("Discrepancy found for n=%d: %d != %d%n", n, result1, result2);
            return false;
        }
        return true;
    }

    // Helper method for improved debugging
    static boolean compareAndLog(FibonacciSumStrategy strategy1, FibonacciSumStrategy strategy2, String name1, String name2, long n) {
        long result1 = strategy1.compute(n);
        long result2 = strategy2.compute(n);
        if (result1 != result2) {
            out.printf("Discrepancy between %s and %s for n=%d: %d != %d%n", name1, name2, n, result1, result2);
            return false;
        }
        return true;
    }

    public static void main(String args[]) {
//        compareMethods();
        stressTest();
    }

    private static void stressTest() {
        // Stress test
//        int maxIterations = 100_000;
        int maxIterations = 50;
        Random random = new Random();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            long n = random.nextInt(60); // Limit n to within the Pisano period
            out.println("Testing with n = " + n);

            // Define strategies
            FibonacciSumStrategy naive = FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaive;
            FibonacciSumStrategy efficient = FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficient;
            FibonacciSumStrategy matrix = FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrix;
            FibonacciSumStrategy functionalNaive = FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaiveFunctional;
            FibonacciSumStrategy functionalEfficient = FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficientFunctional;
            FibonacciSumStrategy functionalMatrix = FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrixFunctional;

            // Check all pairs
            if (!compareAndLog(naive, efficient, "Naive", "Efficient", n) ||
                    !compareAndLog(matrix, efficient, "Matrix", "Efficient", n) ||
                    !compareAndLog(functionalNaive, efficient, "Functional Naive", "Efficient", n) ||
                    !compareAndLog(functionalEfficient, efficient, "Functional Efficient", "Efficient", n) ||
                    !compareAndLog(functionalMatrix, efficient, "Functional Matrix", "Efficient", n)
                // Continue for all other pairs...
            ) {
                // Break the loop if any discrepancy is found
                break;
            }

            out.println("All comparisons passed for n = " + n);
        }
    }

    public static void compareMethods() {
        for (int n = 0; n <= 50; n++) { // Test for a range of n values
            long naiveResult = getFibonacciSumMatrix(n);
            long efficientResult = getFibonacciSumEfficientFunctional(n);
            if (naiveResult != efficientResult) {
                out.println("Discrepancy found for n=" + n + ": Naive=" + naiveResult + ", Efficient Functional=" + efficientResult);
            }
        }
    }

}


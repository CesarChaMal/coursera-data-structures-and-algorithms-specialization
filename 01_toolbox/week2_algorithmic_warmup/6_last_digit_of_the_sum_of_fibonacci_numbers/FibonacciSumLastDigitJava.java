import java.util.*;
import java.util.function.BiFunction;

import static java.lang.System.*;

public class FibonacciSumLastDigitJava {

    // O(n) time complexity
    private static long getFibonacciSumNaive(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current  = 1;
        long sum      = 1;

        for (long i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
            sum += current;
        }

        return sum % 10;
    }

    // O(log n) time complexity using Pisano Period
    private static long getFibonacciSumEfficient(long n) {
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
    private static long getFibonacciSumMatrix(long n) {
        if (n <= 1)
            return n;

        long[][] F = {{1, 1}, {1, 0}};
        power(F, n);

        // Sum of Fibonacci numbers is F[0][0] - 1
        return (F[0][0] - 1 + 10) % 10; // Adding 10 to handle negative values
    }

    private static long getFibonacciSumNaiveHelper(long n, long prev, long curr, long sum) {
        if (n <= 1) {
            return n;
        }
        if (n == 2) {
            return sum;
        }
        return getFibonacciSumNaiveHelper(n - 1, curr, (prev + curr) % 10, (sum + curr) % 10);
    }

    // Functional equivalent of getFibonacciSumNaive
    private static long getFibonacciSumNaiveFunctional(long n) {
        return getFibonacciSumNaiveHelper(n, 0, 1, 1);
    }

    private static long getFibonacciSumEfficientHelper(long n, long prev, long curr, long sum) {
        if (n <= 1) {
            return n;
        }
        if (n == 2) {
            return sum;
        }
        return getFibonacciSumEfficientHelper(n - 1, curr, (prev + curr) % 10, (sum + curr) % 10);
    }

    // Functional equivalent of getFibonacciSumEfficient
    private static long getFibonacciSumEfficientFunctional(long n) {
        n = n % 60; // Pisano Period for modulo 10 is 60
        return getFibonacciSumEfficientHelper(n, 0, 1, 1);
    }

    // Functional equivalent of matrixMultiply
    private static long[][] matrixMultiplyFunctional(long[][] a, long[][] b) {
        BiFunction<Integer, Integer, Long> computeElement = (i, j) -> {
            long sum = 0;
            for (int k = 0; k < 2; k++) {
                sum = (sum + a[i][k] * b[k][j]) % 10;
            }
            return sum;
        };
        return new long[][]{
                {computeElement.apply(0, 0), computeElement.apply(0, 1)},
                {computeElement.apply(1, 0), computeElement.apply(1, 1)}
        };
    }

    // Functional equivalent of matrixPower
    private static long[][] matrixPowerFunctional(long[][] matrix, long n) {
        if (n == 0)
            return new long[][]{{1, 0}, {0, 1}};
        if (n == 1)
            return matrix;
        long[][] halfPower = matrixPowerFunctional(matrix, n / 2);
        long[][] square = matrixMultiplyFunctional(halfPower, halfPower);
        return n % 2 == 0 ? square : matrixMultiplyFunctional(matrix, square);
    }

    // O(log n) time complexity using Matrix Exponentiation
    private static long getFibonacciSumMatrixFunctional(long n) {
        if (n <= 1)
            return n;
        long[][] F = {{1, 1}, {1, 0}};
        return (matrixPowerFunctional(F, n)[0][0] - 1 + 10) % 10; // Adding 10 to handle negative values
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);
        long n = scanner.nextLong();
        long s = getFibonacciSumEfficient(n);
        out.println(s);
    }
}


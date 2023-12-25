import java.util.Random;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.stream.LongStream;

import static java.lang.System.out;

public class GCDJavaStressTest {

    // O(min(a, b)) time complexity
    private static long gcd_naive(long a, long b) {
        long current_gcd = 1;
        for (long d = 2; d <= a && d <= b; ++d) {
            if (a % d == 0 && b % d == 0) {
                if (d > current_gcd) {
                    current_gcd = d;
                }
            }
        }

        return current_gcd;
    }

    // O(log(min(a, b))) time complexity
    // Euclidean Algorithm
    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    // O(min(a, b)) time complexity, functional approach
    private static long gcdFunctionalNaive(long a, long b) {
        return LongStream.rangeClosed(2, Math.min(a, b))
                .filter(d -> a % d == 0 && b % d == 0)
                .max()
                .orElse(1);
    }

    // O(log(min(a, b))) time complexity
    // Euclidean Algorithm
    private static long gcdFunctional(long a, long b) {
        BiFunction<Long, Long, Long> gcdFunc = new BiFunction<>() {
            @Override
            public Long apply(Long a, Long b) {
                return (b == 0) ? a : this.apply(b, a % b);
            }
        };
        return gcdFunc.apply(a, b);
    }

    // Functional interface for strategies
    @FunctionalInterface
    interface GCDStrategy {
        long compute(long a, long b);
    }

    // Method to compare strategies
    static boolean compareStrategies(GCDJavaStressTest.GCDStrategy strategy1, GCDJavaStressTest.GCDStrategy strategy2, long a, long b) {
        long result1 = strategy1.compute(a, b);
        long result2 = strategy2.compute(a, b);
        if (result1 != result2) {
            out.printf("Discrepancy found: %d != %d%n", result1, result2);
            return false;
        }
        return true;
    }

    // Generate a random long within a specified range
    private static long getRandomLong(long max) {
        Random random = new Random();
        return (long) (random.nextDouble() * max);
    }

    public static void main(String args[]) {
        // Stress test
        int maxIterations = 100_000;
        // Maximum range for random numbers
        long maxRange = 100_000_000L; // For example, up to 10 million

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            long a = getRandomLong(maxRange);
            long b = getRandomLong(maxRange);

            out.println("Testing with a = " + a + " and b = " + b);

            GCDStrategy naive = GCDJavaStressTest::gcd_naive;
            GCDStrategy efficient = GCDJavaStressTest::gcd;
            GCDStrategy functionalNaive = GCDJavaStressTest::gcdFunctionalNaive;
            GCDStrategy functional = GCDJavaStressTest::gcdFunctional;

            if (!compareStrategies(naive, efficient, a, b)) {
                out.println("Naive vs Efficient failed");
                break;
            }
            if (!compareStrategies(functionalNaive, efficient, a, b)) {
                out.println("Functional Naive vs Efficient failed");
                break;
            }
            if (!compareStrategies(functional, efficient, a, b)) {
                out.println("Functional vs Efficient failed");
                break;
            }

            out.println("OK");
        }
    }

}
import java.util.Random;
import java.util.function.BiFunction;

import static java.lang.System.out;

public class LCMJavaStressTest {
    // O(a * b) time complexity - Naive Approach
    private static long lcm_naive(long a, long b) {
        for (long l = 1; l <= a * b; ++l)
            if (l % a == 0 && l % b == 0)
                return l;

        return a * b;
    }

    // O(a * b) time complexity - Alternative Naive LCM
    public static long lcm_naive2(long a, long b) {
        long lcm = 1;
        while (true) {
            if (lcm % a == 0 && lcm % b == 0) {
                return lcm;
            }
            lcm++;
        }
    }

    // O(log(min(a, b))) time complexity - Euclidean Algorithm for GCD
    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    // O(log(min(a, b))) time complexity - Efficient LCM using GCD
    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    // O(min(a, b)) time complexity - Efficient LCM using GCD
    private static BiFunction<Long, Long, Long> lcmFunctionalNaive = (a, b) -> {
        long limit = a * b;
        for (long current = 1; current <= limit; current++) {
            if (current % a == 0 && current % b == 0) {
                return current;
            }
        }
        return limit;
    };

    // Functional approach for GCD
    private static BiFunction<Long, Long, Long> gcdFunctional = (a, b) -> {
        BiFunction<Long, Long, Long> euclidGcd = new BiFunction<>() {
            @Override
            public Long apply(Long x, Long y) {
                return (y == 0) ? Long.valueOf(x) : this.apply(y, x % y);
            }
        };
        return euclidGcd.apply(a, b);
    };

    // O(log(min(a, b))) time complexity - Efficient LCM using GCD
    private static BiFunction<Long, Long, Long> lcmFunctional = (a, b) -> {
        return (a * b) / gcdFunctional.apply(a, b);
    };

    // Functional interface for strategies
    @FunctionalInterface
    interface LCMStrategy {
        long compute(long a, long b);
    }

    // Method to compare strategies
    static boolean compareStrategies(LCMJavaStressTest.LCMStrategy strategy1, LCMJavaStressTest.LCMStrategy strategy2, long a, long b) {
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
        long maxRange = 1_000_000L; // For example, up to 10 million

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            long a = getRandomLong(maxRange);
            long b = getRandomLong(maxRange);

            out.println("Testing with a = " + a + " and b = " + b);

            LCMJavaStressTest.LCMStrategy naive = LCMJavaStressTest::lcm_naive;
            LCMJavaStressTest.LCMStrategy naive2 = LCMJavaStressTest::lcm_naive2;
            LCMJavaStressTest.LCMStrategy efficient = LCMJavaStressTest::lcm;
            LCMJavaStressTest.LCMStrategy functionalNaive = lcmFunctionalNaive::apply;
            LCMJavaStressTest.LCMStrategy functional = lcmFunctional::apply;

            if (!compareStrategies(naive, efficient, a, b)) {
                out.println("Naive vs Efficient failed");
                break;
            }
            if (!compareStrategies(naive2, efficient, a, b)) {
                out.println("Naive2 vs Efficient failed");
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

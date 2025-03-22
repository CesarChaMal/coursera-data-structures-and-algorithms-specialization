import java.util.*;
import java.util.function.BiFunction;

import static java.lang.System.out;

public class LCMJava {
    // O(a * b) time complexity - Naive Approach
    private static long lcm_naive1(long a, long b) {
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


    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        // You can use either the naive or the efficient method
        // out.println(lcm_naive(a, b));
        out.println(lcm(a, b));
    }
}

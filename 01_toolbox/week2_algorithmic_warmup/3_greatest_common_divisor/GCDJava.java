import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.LongStream;

import static java.lang.System.*;

public class GCDJava {

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

    public static void main(String args[]) {
        Scanner scanner = new Scanner(in);
        long a = scanner.nextLong();
        long b = scanner.nextLong();

        out.println(gcd(a, b));
    }
}

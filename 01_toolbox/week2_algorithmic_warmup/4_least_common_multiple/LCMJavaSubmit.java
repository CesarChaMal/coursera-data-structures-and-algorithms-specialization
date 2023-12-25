import java.util.Scanner;
import java.util.function.BiFunction;

import static java.lang.System.out;

public class LCMJavaSubmit {
    // O(a * b) time complexity - Naive Approach
    private static long lcm_naive(long a, long b) {
        for (long l = 1; l <= a * b; ++l)
            if (l % a == 0 && l % b == 0)
                return l;

        return a * b;
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

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        // You can use either the naive or the efficient method
        // out.println(lcm_naive(a, b));
        out.println(lcm(a, b));
    }
}

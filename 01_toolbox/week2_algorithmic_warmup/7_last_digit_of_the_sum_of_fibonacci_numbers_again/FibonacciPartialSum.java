import java.util.*;

public class FibonacciPartialSum {
    private static long getFibonacciPartialSumNaive(long from, long to) {
        long sum = 0;

        long current = 0;
        long next  = 1;

        for (long i = 0; i <= to; ++i) {
            if (i >= from) {
                sum += current;
            }

            long new_current = next;
            next = next + current;
            current = new_current;
        }

        return sum % 10;
    }

    private static long getPisanoPeriod(long m) {
        long prev = 0, curr = 1;
        for (long i = 0; i < m * m; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;

            if (prev == 0 && curr == 1) {
                return i + 1;
            }
        }
        return 0;
    }

    private static long getFibonacciMod(long n, long m) {
        long period = getPisanoPeriod(m);
        n %= period;

        long prev = 0, curr = 1;
        if (n <= 1) return n;

        for (long i = 2; i <= n; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
        }

        return curr;
    }

    private static long getFibonacciSum(long n) {
        // sum of F(0) to F(n) = F(n+2) - 1
        return (getFibonacciMod(n + 2, 10) + 9) % 10;
    }

    private static long getFibonacciPartialSumFast(long from, long to) {
        long sumTo = getFibonacciSum(to);
        long sumFromMinus1 = getFibonacciSum(from - 1);
        return (sumTo - sumFromMinus1 + 10) % 10;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long from = scanner.nextLong();
        long to = scanner.nextLong();
        System.out.println(getFibonacciPartialSumFast(from, to));
    }
}


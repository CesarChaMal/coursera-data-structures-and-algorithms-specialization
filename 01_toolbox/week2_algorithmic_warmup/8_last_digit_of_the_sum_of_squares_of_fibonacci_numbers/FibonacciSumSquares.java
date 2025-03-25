import java.util.*;

public class FibonacciSumSquares {
    private static long getFibonacciSumSquaresNaive(long n) {
        if (n <= 1)
            return n;

        long previous = 0;
        long current  = 1;
        long sum      = 1;

        for (long i = 0; i < n - 1; ++i) {
            long tmp_previous = previous;
            previous = current;
            current = tmp_previous + current;
            sum += current * current;
        }

        return sum % 10;
    }

    private static long getPisanoPeriod(long m) {
        long prev = 0, curr = 1;
        for (long i = 0; i < m * m; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;

            if (prev == 0 && curr == 1)
                return i + 1;
        }
        return 0;
    }

    private static long getFibonacciMod(long n, long m) {
        long period = getPisanoPeriod(m);
        n %= period;

        long prev = 0, curr = 1;
        if (n == 0) return 0;
        if (n == 1) return 1;

        for (long i = 2; i <= n; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
        }

        return curr;
    }

    private static long getFibonacciSumSquaresFast(long n) {
        long Fn = getFibonacciMod(n, 10);
        long FnPlus1 = getFibonacciMod(n + 1, 10);
        return (Fn * FnPlus1) % 10;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long n = scanner.nextLong();
        System.out.println(getFibonacciSumSquaresFast(n));
    }
}


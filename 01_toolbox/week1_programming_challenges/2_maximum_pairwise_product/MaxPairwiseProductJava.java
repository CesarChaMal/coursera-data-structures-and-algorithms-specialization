import java.util.*;
import java.io.*;

import static java.lang.System.out;

public class MaxPairwiseProductJava {

    // O(n^2) time complexity
    static int getMaxPairwiseProductNaive(int[] numbers) {
        int max_product = 0;
        int n = numbers.length;

        for (int first = 0; first < n; ++first) {
            for (int second = first + 1; second < n; ++second) {
                if (numbers[first] * numbers[second] > max_product) {
                    max_product = numbers[first] * numbers[second];
                }
//                max_product = Math.max(max_product, numbers[first] * numbers[second]);
            }
        }

        return max_product;
    }

    // O(n) time complexity
    static int getMaxPairwiseProduct(int[] numbers) {
        int max_product = 0;
        int n = numbers.length;

        int max1 = Math.max(numbers[0], numbers[1]);
        int max2 = Math.min(numbers[0], numbers[1]);

        for (int i = 2; i < n; ++i) {
            if (numbers[i] > max1) {
                max2 = max1;
                max1 = numbers[i];
            } else if (numbers[i] > max2) {
                max2 = numbers[i];
            }
        }

        max_product = max1 * max2;
        return max_product;
    }

    // O(n log n)
    static int getMaxPairwiseProductFunctionalNaive(int[] numbers) {
        return Arrays.stream(numbers)
                .boxed()
                .sorted((a, b) -> b - a) // Sort in descending order
                .limit(2) // Take the first two elements (the largest ones)
                .reduce(1, (a, b) -> a * b); // Multiply them together
    }

    // O(n) time complexity, functional approach
    static int getMaxPairwiseProductFunctional(int[] numbers) {
        int[] result = Arrays.stream(numbers)
                .boxed()
                .reduce(new int[]{-1, -1}, (acc, current) -> {
                    if (current > acc[0]) {
                        acc[1] = acc[0];
                        acc[0] = current;
                    } else if (current > acc[1]) {
                        acc[1] = current;
                    }
                    return acc;
                }, (acc1, acc2) -> acc1); // Combiner is not used in sequential streams

        return result[0] * result[1];
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        out.println(getMaxPairwiseProduct(numbers));
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new
                    InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

}

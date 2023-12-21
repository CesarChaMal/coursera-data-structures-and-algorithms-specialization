import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

import static java.lang.System.out;

public class MaxPairwiseProductJavaStressTest {

    // O(n^2) time complexity
    static long getMaxPairwiseProductNaive(long[] numbers) {
        long max_product = 0;
        int n = numbers.length;

        for (int first = 0; first < n; ++first) {
            for (int second = first + 1; second < n; ++second) {
/*
                if (numbers[first] * numbers[second] > max_product) {
                    max_product = numbers[first] * numbers[second];
                }
*/
                max_product = Math.max(max_product, numbers[first] * numbers[second]);
            }
        }

        return max_product;
    }

    // O(n) time complexity
    static long getMaxPairwiseProduct(long[] numbers) {
        long max_product = 0;
        int n = numbers.length;

        long max1 = Math.max(numbers[0], numbers[1]);
        long max2 = Math.min(numbers[0], numbers[1]);

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

    // O(n log n) time complexity, functional approach
    static long getMaxPairwiseProductFunctionalNaive(long[] numbers) {
/*
        return Arrays.stream(numbers)
                .boxed()
                .sorted((a, b) -> b - a) // Sort in descending order
                .limit(2) // Take the first two elements (the largest ones)
                .reduce(1, (a, b) -> a * b); // Multiply them together
*/
        return Arrays.stream(numbers)
                .boxed()
                .sorted((a, b) -> Long.compare(b, a)) // Sort in descending order
                .limit(2) // Take the first two elements (the largest ones)
                .reduce(1L, (a, b) -> a * b); // Multiply them together
    }

    // O(n) time complexity, functional approach
    static long getMaxPairwiseProductFunctional(long[] numbers) {
        long[] result = Arrays.stream(numbers)
                .boxed()
                .reduce(new long[]{-1, -1}, (acc, current) -> {
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
        // Stress test
        int maxIterations = 100_000;
//        while (true) {
        for (int iteration = 0; iteration < maxIterations; iteration++) {
                Random random = new Random();
                int n = random.nextInt(1_000) + 2;
//                int n = random.nextInt(100) + 2;
//                int n = random.nextInt(10) + 2;
//                int n = random.nextInt(4) + 2;

//            int random = (int) (Math.random() * Integer.MAX_VALUE);
//            int n = random % 1_000 + 2;
//            int n = random % 100 + 2;
//            int n = random % 10 + 2;
//            int n = random % 4 + 2;

            out.println(n);
            long[] numbers = new long[n];
            for (int i = 0; i < n; i++) {
                    numbers[i] = random.nextInt(100_000);
//                    numbers[i] = random.nextInt(10_000);
//                    numbers[i] = random.nextInt(1_000);
//                    numbers[i] = random.nextInt(100);
//                    numbers[i] = random.nextInt(10);

//                numbers[i] = (int) (Math.random() * 100_000);
//                numbers[i] = (int) (Math.random() * 10_000);
//                numbers[i] = (int) (Math.random() * 1_000);
//                numbers[i] = (int) (Math.random() * 1000);
//                numbers[i] = (int) (Math.random() * 10);
            }

/*
            out.print("Array: ");
            for (int i = 0; i < n; i++) {
                out.print(numbers[i] + " ");
            }
*/

            printArray(numbers);

/*
            out.println();
            int result1 = getMaxPairwiseProduct(numbers);
            int result2 = getMaxPairwiseProductFunctional(numbers);
            if (result1 != result2) {
                out.println("Wrong answer: " + result1 + " " + result2);
                break;
            } else {
                out.println("OK");
            }
*/

/*
            out.println();
            int resultNaive = getMaxPairwiseProductNaive(numbers);
            int resultEfficient = getMaxPairwiseProduct(numbers);
            int resultFunctional = getMaxPairwiseProductFunctional(numbers);
            int resultFunctionalNaive = getMaxPairwiseProductFunctionalNaive(numbers);

            boolean isNaiveCorrect = resultNaive == resultEfficient;
            boolean isFunctionalCorrect = resultFunctional == resultEfficient;
            boolean isFunctionalNaiveCorrect = resultFunctionalNaive == resultEfficient;

            if (!isNaiveCorrect || !isFunctionalCorrect || !isFunctionalNaiveCorrect) {
                System.out.println("Discrepancy found:");
                if (!isNaiveCorrect) {
                    System.out.println("Naive vs Efficient: " + resultNaive + " != " + resultEfficient);
                }
                if (!isFunctionalCorrect) {
                    System.out.println("Functional vs Efficient: " + resultFunctional + " != " + resultEfficient);
                }
                if (!isFunctionalNaiveCorrect) {
                    System.out.println("Functional Naive vs Efficient: " + resultFunctionalNaive + " != " + resultEfficient);
                }
                break;
            } else {
                System.out.println("OK");
            }
*/

            // Define strategies
            MaxPairwiseProductStrategy naive = MaxPairwiseProductJavaStressTest::getMaxPairwiseProductNaive;
            MaxPairwiseProductStrategy efficient = MaxPairwiseProductJavaStressTest::getMaxPairwiseProduct;
            MaxPairwiseProductStrategy functional = MaxPairwiseProductJavaStressTest::getMaxPairwiseProductFunctional;
            MaxPairwiseProductStrategy functionalNaive = MaxPairwiseProductJavaStressTest::getMaxPairwiseProductFunctionalNaive;

            if (!compareStrategies(naive, efficient, numbers)) {
                out.println("Naive vs Efficient failed");
                break;
            }
            if (!compareStrategies(functional, efficient, numbers)) {
                out.println("Functional vs Efficient failed");
                break;
            }
            if (!compareStrategies(functionalNaive, efficient, numbers)) {
                out.println("Functional Naive vs Efficient failed");
                break;
            }

            out.println("OK");
        }
    }

    @FunctionalInterface
    interface MaxPairwiseProductStrategy {
        long compute(long[] numbers);
    }

    static boolean compareStrategies(MaxPairwiseProductStrategy strategy1, MaxPairwiseProductStrategy strategy2, long[] numbers) {
        long result1 = strategy1.compute(numbers);
        long result2 = strategy2.compute(numbers);
        if (result1 != result2) {
            out.printf("Discrepancy found: %d != %d%n", result1, result2);
            return false;
        }
        return true;
    }

    static void printArray(long[] numbers) {
        for (long number : numbers) {
            out.print(number + " ");
        }
        out.println();
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

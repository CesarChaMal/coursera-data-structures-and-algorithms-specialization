import java.util.Random;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleImmutableEntry;

import static java.lang.System.out;

public class FibonacciLastDigitJavaStressTest {

  // O(n) time complexity
  private static long getFibonacciLastDigitNaive(int n) {
    if (n <= 1) return n;

    long prev = 0;
    long current = 1;
    for (int i = 2; i <= n; i++) {
      long tmp_previous = prev;
      prev = current;
      current = tmp_previous + current;
    }
    return current % 10;
  }

  // O(n) time complexity
  private static long getFibonacciLastDigit(int n) {
    if (n <= 1) return n;

    long prev = 0;
    long current = 1;
    for (int i = 2; i <= n; i++) {
      long tmp_previous = prev;
      prev = current;
      current = (tmp_previous + current) % 10; // Compute only the last digit
    }
    return current;
  }

  // O(n) time complexity
  public static long getFibonacciLastDigitFunctionalNaive(int n) {
    return Stream.iterate(new SimpleImmutableEntry<>(0L, 1L),
                    entry -> new SimpleImmutableEntry<>(entry.getValue(), entry.getKey() + entry.getValue()))
            .limit(n + 1)
            .map(entry -> entry.getKey())
            .reduce((first, second) -> second)
            .orElseThrow()
            % 10;
  }

  // O(n) time complexity
  public static int getFibonacciLastDigitFunctional(int n) {
    return Stream.iterate(new SimpleImmutableEntry<>(0, 1),
                    entry -> new SimpleImmutableEntry<>(entry.getValue(), (entry.getKey() + entry.getValue()) % 10))
            .limit(n + 1)
            .map(entry -> entry.getKey())
            .reduce((first, second) -> second)
            .orElseThrow();
  }

  // Functional interface for strategies
  @FunctionalInterface
  interface FibonacciLastDigitStrategy {
    long compute(int n);
  }

  // Method to compare strategies
  static boolean compareStrategies(FibonacciLastDigitStrategy strategy1, FibonacciLastDigitStrategy strategy2, int n) {
    long result1 = strategy1.compute(n);
    long result2 = strategy2.compute(n);
    if (result1 != result2) {
      out.printf("Discrepancy found: %d != %d%n", result1, result2);
      return false;
    }
    return true;
  }
  public static void main(String args[]) {
    // Stress test
    int maxIterations = 100_000;
    Random random = new Random();

    for (int iteration = 0; iteration < maxIterations; iteration++) {
      int n = random.nextInt(40); // Limit n to a reasonable range for the naive method

      out.println("Testing with n = " + n);

      FibonacciLastDigitStrategy naive = FibonacciLastDigitJavaStressTest::getFibonacciLastDigitNaive;
      FibonacciLastDigitStrategy efficient = FibonacciLastDigitJavaStressTest::getFibonacciLastDigit;
      FibonacciLastDigitStrategy functionalNaive = FibonacciLastDigitJavaStressTest::getFibonacciLastDigitFunctionalNaive;
      FibonacciLastDigitStrategy functional = FibonacciLastDigitJavaStressTest::getFibonacciLastDigitFunctional;

      // Individual comparisons for each pair of strategies
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaive, FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficient, n)) {
        out.println("Naive vs Efficient failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaive, FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrix, n)) {
        out.println("Naive vs Matrix failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaive, FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaiveFunctional, n)) {
        out.println("Naive vs Functional Naive failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaive, FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficientFunctional, n)) {
        out.println("Naive vs Functional Efficient failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaive, FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrixFunctional, n)) {
        out.println("Naive vs Functional Matrix failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficient, FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrix, n)) {
        out.println("Efficient vs Matrix failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficient, FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaiveFunctional, n)) {
        out.println("Efficient vs Functional Naive failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficient, FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficientFunctional, n)) {
        out.println("Efficient vs Functional Efficient failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficient, FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrixFunctional, n)) {
        out.println("Efficient vs Functional Matrix failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrix, FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaiveFunctional, n)) {
        out.println("Matrix vs Functional Naive failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrix, FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficientFunctional, n)) {
        out.println("Matrix vs Functional Efficient failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrix, FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrixFunctional, n)) {
        out.println("Matrix vs Functional Matrix failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaiveFunctional, FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficientFunctional, n)) {
        out.println("Functional Naive vs Functional Efficient failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumNaiveFunctional, FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrixFunctional, n)) {
        out.println("Functional Naive vs Functional Matrix failed");
        break;
      }
      if (!compareStrategies(FibonacciSumLastDigitJavaStressTest::getFibonacciSumEfficientFunctional, FibonacciSumLastDigitJavaStressTest::getFibonacciSumMatrixFunctional, n)) {
        out.println("Functional Efficient vs Functional Matrix failed");
        break;
      }

      out.println("OK");
//      Scanner in = new Scanner(System.in);
//      int n = in.nextInt();
//      out.println(calcFib(n));
    }
  }

}

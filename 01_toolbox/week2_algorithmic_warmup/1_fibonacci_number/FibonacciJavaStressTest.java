import java.util.AbstractMap;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.Random;
import static java.lang.System.out;

public class FibonacciJavaStressTest {

  // O(2^n) time complexity
  private static long calcFibNaive(int n) {
    if (n <= 1)
      return n;

    return calcFibNaive(n - 1) + calcFibNaive(n - 2);
  }

  // O(n) time complexity
  private static long calcFib(int n) {
    if (n <= 1) return n;

    long prev = 0;
    long current = 1;
    for (int i = 2; i <= n; i++) {
      long newCurrent = prev + current;
      prev = current;
      current = newCurrent;
    }
    return current;
  }

  // Functional interface for recursive lambda
  @FunctionalInterface
  interface FibonacciCalculator extends UnaryOperator<Long> {
    default FibonacciCalculator compose(FibonacciCalculator before) {
      return value -> apply(before.apply(value));
    }
  }

  // Y-combinator method to enable recursion in lambda
  private static FibonacciCalculator yCombinator(final UnaryOperator<FibonacciCalculator> operator) {
    return t -> operator.apply(yCombinator(operator)).apply(t);
  }

  // O(2^n) time complexity, functional approach
  public static long calcFibFunctionalNaive(int n) {
    FibonacciCalculator fibCalc = yCombinator(f -> num -> (num <= 1) ? num : f.apply(num - 1) + f.apply(num - 2));
    return fibCalc.apply((long) n);
  }

  // O(n) time complexity, functional approach
  private static long calcFibFunctional(int n) {
    return Stream.iterate(new AbstractMap.SimpleEntry<>(0L, 1L),
                    entry -> new AbstractMap.SimpleEntry<>(entry.getValue(), entry.getKey() + entry.getValue()))
            .limit(n + 1)
            .map(AbstractMap.SimpleEntry::getKey)
            .reduce((first, second) -> second) // This gets the last element of the stream
            .orElseThrow();
  }

  // Functional interface for strategies
  @FunctionalInterface
  interface FibonacciStrategy {
    long compute(int n);
  }

  // Method to compare strategies
  static boolean compareStrategies(FibonacciStrategy strategy1, FibonacciStrategy strategy2, int n) {
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

      FibonacciStrategy naive = FibonacciJavaStressTest::calcFibNaive;
      FibonacciStrategy efficient = FibonacciJavaStressTest::calcFib;
      FibonacciStrategy functional = FibonacciJavaStressTest::calcFibFunctional;
      FibonacciStrategy functionalNaive = FibonacciJavaStressTest::calcFibFunctionalNaive;

      if (!compareStrategies(naive, efficient, n)) {
        out.println("Naive vs Efficient failed");
        break;
      }
      if (!compareStrategies(functionalNaive, efficient, n)) {
        out.println("Functional Naive vs Efficient failed");
        break;
      }

      if (!compareStrategies(functional, efficient, n)) {
        out.println("Functional vs Efficient failed");
        break;
      }

      out.println("OK");

//      Scanner in = new Scanner(System.in);
//      int n = in.nextInt();
//      out.println(calcFib(n));
    }
  }

}

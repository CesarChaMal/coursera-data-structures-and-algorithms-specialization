import java.util.AbstractMap;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.function.UnaryOperator;
public class FibonacciJava {

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

  // Method to calculate Fibonacci using a functional approach
  public static long calcFibFunctionalNaive(int n) {
    FibonacciCalculator fibCalc = yCombinator(f -> num -> (num <= 1) ? num : f.apply(num - 1) + f.apply(num - 2));
    return fibCalc.apply((long) n);
  }

  // O(n) time complexity
  private static long calcFibFunctional(int n) {
    return Stream.iterate(new AbstractMap.SimpleEntry<>(0L, 1L),
                    entry -> new AbstractMap.SimpleEntry<>(entry.getValue(), entry.getKey() + entry.getValue()))
            .limit(n + 1)
            .map(AbstractMap.SimpleEntry::getKey)
            .reduce((first, second) -> second) // This gets the last element of the stream
            .orElseThrow();
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();

    System.out.println(calcFib(n));
  }
}

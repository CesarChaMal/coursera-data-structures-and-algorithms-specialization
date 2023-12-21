import java.util.Scanner;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleImmutableEntry;

public class FibonacciLastDigitJava {

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

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt();

    System.out.println(getFibonacciLastDigit(n));
  }
}

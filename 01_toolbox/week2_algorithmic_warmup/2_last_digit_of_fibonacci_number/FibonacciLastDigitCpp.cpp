#include <iostream>
#include <cassert>
#include <functional>
#include <random>

// The following code calls a naive algorithm for computing a Fibonacci number.
//
// What to do:
// 1. Compile the following code and run it on an input "40" to check that it is slow.
//    You may also want to submit it to the grader to ensure that it gets the "time limit exceeded" message.
// 2. Implement the fibonacci_fast procedure.
// 3. Remove the line that prints the result of the naive algorithm, comment the lines reading the input,
//    uncomment the line with a call to test_solution, compile the program, and run it.
//    This will ensure that your efficient algorithm returns the same as the naive one for small values of n.
// 4. If test_solution() reveals a bug in your implementation, debug it, fix it, and repeat step 3.
// 5. Remove the call to test_solution, uncomment the line with a call to fibonacci_fast (and the lines reading the input),
//    and submit it to the grader.

// O(n) time complexity - Naive Iterative Approach
int fibonacci_last_digit_naive(int n) {
    if (n <= 1) return n;
    int prev = 0, current = 1;
    for (int i = 2; i <= n; i++) {
        int tmp_previous = prev;
        prev = current;
        current = tmp_previous + current;
    }
    return current % 10;
}

// O(n) time complexity - Naive Iterative Approach
int fibonacci_last_digit(int n) {
    if (n <= 1) return n;
    int prev = 0, current = 1;
    for (int i = 2; i <= n; i++) {
        int tmp_previous = prev;
        prev = current;
        current = (tmp_previous + current) % 10;
    }
    return current;
}

// O(n) time complexity, functional naive approach
int fibonacci_last_digit_functional_naive(int n) {
    std::function<int(int, int, int)> fib = [&fib](int n, int a, int b) -> int {
        return n == 0 ? a : fib(n - 1, b, (a + b) % 10);
    };
    return fib(n, 0, 1);
}

// O(n) time complexity, functional approach (Efficient)
int fibonacci_last_digit_functional(int n) {
    if (n <= 1) return n;
    std::pair<int, int> fib_pair = {0, 1};
    for (int i = 2; i <= n; ++i) {
        fib_pair = {fib_pair.second, (fib_pair.first + fib_pair.second) % 10};
    }
    return fib_pair.second;
}

void test_solution() {
    assert(fibonacci_last_digit(21) == 6);
    for (int n = 0; n < 20; ++n)
        assert(fibonacci_last_digit(n) == fibonacci_last_digit_functional_naive(n));
}

int main() {
    int n = 0;
    std::cin >> n;

    //std::cout << fibonacci_last_digit_naive(n) << '\n';
    test_solution();
    std::cout << fibonacci_last_digit(n) << '\n';
    return 0;
}

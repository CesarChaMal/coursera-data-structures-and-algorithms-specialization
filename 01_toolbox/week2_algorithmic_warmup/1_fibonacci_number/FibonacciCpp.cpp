#include <iostream>
#include <cassert>
#include <functional>
#include <random>

// The following code calls a naive algorithm for computing a Fibonacci number.
//
// What to do:
// 1. Compile the following code and run it on an input "40" to check that it is slow.
//    You may also want to submit it to the grader to ensure that it gets the "time limit exceeded" message.
// 2. Implement the fibonacci procedure.
// 3. Remove the line that prints the result of the naive algorithm, comment the lines reading the input,
//    uncomment the line with a call to test_solution, compile the program, and run it.
//    This will ensure that your efficient algorithm returns the same as the naive one for small values of n.
// 4. If test_solution() reveals a bug in your implementation, debug it, fix it, and repeat step 3.
// 5. Remove the call to test_solution, uncomment the line with a call to fibonacci (and the lines reading the input),
//    and submit it to the grader.

// O(2^n) time complexity - Naive Recursive Approach
int fibonacci_naive(int n) {
    if (n <= 1) return n;
    return fibonacci_naive(n - 1) + fibonacci_naive(n - 2);
}

// O(n) time complexity - Efficient Iterative Approach
int fibonacci(int n) {
    if (n <= 1) return n;
    int prev = 0, current = 1;
    for (int i = 2; i <= n; i++) {
        int newCurrent = prev + current;
        prev = current;
        current = newCurrent;
    }
    return current;
}

// O(2^n) time complexity - Functional Naive Recursive Approach
std::function<int(int)> fibonacci_functional_naive = [](int n) -> int {
    std::function<int(int)> fib = [&](int n) -> int {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    };
    return fib(n);
};

// O(n) time complexity - Functional Iterative Approach
std::function<int(int)> fibonacci_functional = [](int n) -> int {
    if (n <= 1) return n;

    int prev = 0, current = 1;
    std::function<void()> update = [&]() {
        int newCurrent = prev + current;
        prev = current;
        current = newCurrent;
    };

    for (int i = 2; i <= n; ++i) {
        update();
    }
    return current;
};

void test_solution() {
    assert(fibonacci(3) == 2);
    assert(fibonacci(10) == 55);
    for (int n = 0; n < 20; ++n)
        assert(fibonacci(n) == fibonacci_naive(n));
}

int main() {
    int n = 0;
    std::cin >> n;

    std::cout << fibonacci_naive(n) << '\n';
    //test_solution();
    //std::cout << fibonacci(n) << '\n';
    return 0;
}

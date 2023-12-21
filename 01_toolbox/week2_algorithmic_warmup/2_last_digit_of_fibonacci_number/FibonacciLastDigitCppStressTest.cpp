#include <iostream>
#include <cassert>
#include <functional>
#include <random>

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
        assert(fibonacci_last_digit(n) == fibonacci_last_digit_naive(n));
}

// Stress Test
void stressTest(int maxIterations, int maxN) {
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> dis(0, maxN);

    for (int i = 0; i < maxIterations; ++i) {
        int n = dis(gen);
        std::cout << "Testing n = " << n << std::endl;

        assert(fibonacci_last_digit_naive(n) == fibonacci_last_digit(n));
        assert(fibonacci_last_digit_functional_naive(n) == fibonacci_last_digit(n));
        assert(fibonacci_last_digit_functional(n) == fibonacci_last_digit(n));
    }

    std::cout << "Stress test passed" << std::endl;
}

int main() {
//    int n = 0;
//    std::cin >> n;

    // Uncomment the line below to run the stress test
    stressTest(1000000, 40);

    //std::cout << fibonacci_fast(n) << '\n';

    // Uncomment to test the solution
    // test_solution();

    return 0;
}

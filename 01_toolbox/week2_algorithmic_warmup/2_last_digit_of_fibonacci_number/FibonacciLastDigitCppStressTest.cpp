#include <iostream>
#include <cassert>
#include <functional>
#include <random>
#include <utility>

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

// O(n) time complexity - Efficient Iterative Approach
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

// O(n) time complexity - Functional Naive Recursive Approach
std::function<int(int)> fibonacci_last_digit_functional_naive = [](int n) -> int {
    std::function<int(int)> fib = [&](int n) -> int {
        if (n <= 1) return n;
        return (fib(n - 1) + fib(n - 2)) % 10;
    };
    return fib(n);
};

// O(n) time complexity - Functional Iterative Approach
std::function<int(int)> fibonacci_last_digit_functional = [](int n) -> int {
    if (n <= 1) return n;

    int prev = 0, current = 1;
    std::function<void()> update = [&]() {
        int newCurrent = (prev + current) % 10;
        prev = current;
        current = newCurrent;
    };

    for (int i = 2; i <= n; ++i) {
        update();
    }
    return current;
};

bool CompareStrategies(int n) {
    int resultNaive = fibonacci_last_digit_naive(n);
    int resultEfficient = fibonacci_last_digit(n);
    int resultFunctionalNaive = fibonacci_last_digit_functional_naive(n);
    int resultFunctional = fibonacci_last_digit_functional(n);

    if (resultNaive != resultEfficient ||
        resultNaive != resultFunctionalNaive ||
        resultNaive != resultFunctional) {
        std::cout << "Discrepancy found at n=" << n << ":\n";
        std::cout << "Naive: " << resultNaive << "\n";
        std::cout << "Efficient: " << resultEfficient << "\n";
        std::cout << "Functional Naive: " << resultFunctionalNaive << "\n";
        std::cout << "Functional: " << resultFunctional << "\n";
        return false;
    }
    return true;
}

// Stress Test
void stressTest(int maxIterations, int maxN) {
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> dis(0, maxN);

    for (int i = 0; i < maxIterations; ++i) {
        int n = dis(gen);
        std::cout << "Testing n = " << n << std::endl;

        if (!CompareStrategies(n)) {
            std::cout << "Mismatch found in iteration " << i + 1 << "\n";
            break;
        }
    }

    std::cout << "Stress test passed" << std::endl;
}

int main() {
    // Uncomment the line below to run the stress test
    stressTest(10000, 40); // Adjust maxIterations and maxN as needed

    // Uncomment to run with user input
    /*
    int n;
    std::cin >> n;
    std::cout << fibonacci_last_digit(n) << "\n";
    */
    return 0;
}

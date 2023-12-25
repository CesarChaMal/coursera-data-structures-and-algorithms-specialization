#include <iostream>
#include <functional>
#include <random>
#include <cassert>

// O(min(a, b)) time complexity
long long gcd_naive(long long a, long long b) {
    long long current_gcd = 1;
    for (long long d = 2; d <= a && d <= b; ++d) {
        if (a % d == 0 && b % d == 0) {
            current_gcd = d;
        }
    }
    return current_gcd;
}

// O(log(min(a, b))) time complexity - Euclidean Algorithm
long long gcd(long long a, long long b) {
    return b == 0 ? a : gcd(b, a % b);
}

// O(min(a, b)) time complexity - Functional Naive Approach
std::function<long long(long long, long long)> gcdFunctionalNaive = [](long long a, long long b) -> long long {
    long long currentGcd = 1;
    std::function<void(long long)> updateGcd = [&](long long divisor) {
        if (a % divisor == 0 && b % divisor == 0) {
            currentGcd = divisor;
        }
    };

    for (long long divisor = 2; divisor <= std::min(a, b); ++divisor) {
        updateGcd(divisor);
    }
    return currentGcd;
};

// O(log(min(a, b))) time complexity - Functional Approach
std::function<long long(long long, long long)> gcdFunctional = [](long long a, long long b) -> long long {
    std::function<long long(long long, long long)> euclidGcd = [&](long long x, long long y) -> long long {
        return y == 0 ? x : euclidGcd(y, x % y);
    };
    return euclidGcd(a, b);
};

bool CompareStrategies(long long a, long long b) {
    auto resultNaive = gcd_naive(a, b);
    auto resultEfficient = gcd(a, b);
    auto resultFunctionalNaive = gcdFunctionalNaive(a, b);
    auto resultFunctional = gcdFunctional(a, b);

    if (resultNaive != resultEfficient ||
        resultNaive != resultFunctionalNaive ||
        resultNaive != resultFunctional) {
        std::cout << "Discrepancy found at a=" << a << ", b=" << b << ":\n";
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
    std::uniform_int_distribution<long long> dis(1, maxN);

    for (int i = 0; i < maxIterations; ++i) {
        long long a = dis(gen);
        long long b = dis(gen);
        std::cout << "Testing a = " << a << " and b = " << a << std::endl;

        if (!CompareStrategies(a, b)) {
            std::cout << "Mismatch found in iteration " << i + 1 << "\n";
            break;
        }
    }

    std::cout << "Stress test passed" << std::endl;
}

int main() {
    stressTest(10000, 1000000); // Adjust maxIterations and maxN as needed
    return 0;
}

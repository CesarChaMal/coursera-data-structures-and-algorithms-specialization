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

int main() {
    long long a, b;
    std::cin >> a >> b;
    std::cout << gcd(a, b) << std::endl;
    return 0;
}

#include <iostream>
#include <functional>
#include <random>

// O(a * b) time complexity - Naive Approach
long long lcm_naive(long long a, long long b) {
  for (long l = 1; l <= (long long) a * b; ++l)
    if (l % a == 0 && l % b == 0)
      return l;

  return (long long) a * b;
}

// O(a * b) time complexity - Alternative Naive LCM
long long lcm_naive2(long long a, long long b) {
    long long lcm = 1;
    while (true) {
        if (lcm % a == 0 && lcm % b == 0)
            return lcm;
        lcm++;
    }
}

long long gcd(long long a, long long b) {
    if (b == 0)
        return a;
    else
        return gcd(b, a % b);
}

// O(log(min(a, b))) time complexity - Efficient LCM using GCD
long long lcm(long long a, long long b) {
    return ((long long) a * b) / gcd(a, b);
}

// Functional approach for GCD
std::function<long long(long long, long long)> gcd_functional = [](long long a, long long b) -> long long {
    if (b == 0)
        return a;
    return gcd_functional(b, a % b);
};

// O(log(min(a, b))) time complexity - Efficient LCM using Functional GCD
std::function<long long(long long, long long)> lcm_functional = [](long long a, long long b) -> long long {
    return ((long long) a * b) / gcd_functional(a, b);
};

bool CompareStrategies(int a, int b) {
    auto resultNaive = lcm_naive(a, b);
    auto resultNaive2 = lcm_naive2(a, b);
    auto resultEfficient = lcm(a, b);
    auto resultFunctional = lcm_functional(a, b);

    if (resultNaive != resultEfficient ||
        resultNaive2 != resultEfficient ||
        resultNaive != resultFunctional ||
        resultNaive2 != resultFunctional) {
        std::cout << "Discrepancy found at a=" << a << ", b=" << b << ":\n";
        std::cout << "Naive: " << resultNaive << "\n";
        std::cout << "Naive2: " << resultNaive2 << "\n";
        std::cout << "Efficient: " << resultEfficient << "\n";
        std::cout << "Functional: " << resultFunctional << "\n";
        return false;
    }
    return true;
}

// Stress Test
void stressTest(int maxIterations, int maxN) {
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<int> dis(1, maxN);

    for (int i = 0; i < maxIterations; ++i) {
        int a = dis(gen);
        int b = dis(gen);
        std::cout << "Testing a = " << a << " and b = " << b << std::endl;

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

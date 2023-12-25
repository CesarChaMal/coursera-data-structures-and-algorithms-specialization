#include <iostream>
#include <functional>

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

int main() {
  long long a, b;
  std::cin >> a >> b;
  std::cout << lcm_naive(a, b) << std::endl;
  return 0;
}

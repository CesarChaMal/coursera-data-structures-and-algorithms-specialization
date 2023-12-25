#include <iostream>
#include <functional>  // Include this for std::function

// O(min(a, b)) time complexity
long long gcd_naive(long long a, long long b) {
    long long current_gcd = 1;
    for (long long d = 2; d <= a && d <= b; d++) {
        if (a % d == 0 && b % d == 0) {
            if (d > current_gcd) {
                current_gcd = d;
            }
        }
    }
    return current_gcd;
}

// O(log(min(a, b))) time complexity
// Euclidean Algorithm
long long gcd(long long a, long long b) {
    return b == 0 ? a : gcd(b, a % b);
}

int main() {
    long long a, b;
    std::cin >> a >> b;
    std::cout << gcd(a, b) << std::endl;
    return 0;
}

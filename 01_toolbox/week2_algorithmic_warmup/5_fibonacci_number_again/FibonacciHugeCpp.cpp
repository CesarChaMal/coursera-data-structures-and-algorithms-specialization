#include <iostream>
#include <vector>
#include <functional>

// O(N) time complexity
long long get_fibonacci_huge_naive1(long long n, long long m) {
    if (n <= 1)
        return n;

    long long previous = 0;
    long long current  = 1;

    for (long long i = 0; i < n - 1; ++i) {
        long long tmp_previous = previous;
        previous = current;
        current = tmp_previous + current;
    }

    return current % m;
}

// O(N) time complexity
long long getPisanoPeriod(long long m) {
    long long previous = 0;
    long long current = 1;
    long long res = 0;

    for (long long i = 0; i < m * m; i++) {
        long long temp = current;
        current = (previous + current) % m;
        previous = temp;

        if (previous == 0 && current == 1)
            return i + 1;
    }
    return res;
}

// O(N) time complexity
long long get_fibonacci_huge_naive2(long long n, long long m) {
    long long pisanoPeriod = getPisanoPeriod(m);
    n = n % pisanoPeriod;

    long long previous = 0;
    long long current = 1;

    if (n <= 1)
        return n;

    for (long long i = 0; i < n - 1; i++) {
        long long temp = current;
        current = (previous + current) % m;
        previous = temp;
    }

    return current;
}

// O(N) time complexity
long long get_fibonacci_huge_naive3(long long n, long long m) {
    if (n <= 1)
        return n;

    long long previous = 0;
    long long current = 1;
    for (long long i = 0; i < n - 1; ++i) {
        long long next = (previous + current) % m;
        previous = current;
        current = next;
    }
    return current;
}

std::vector<std::vector<long long>> matrixMultiply(const std::vector<std::vector<long long>>& a, const std::vector<std::vector<long long>>& b, long long m) {
    std::vector<std::vector<long long>> result(2, std::vector<long long>(2, 0));
    for (int i = 0; i < 2; i++) {
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 2; k++) {
                result[i][j] = (result[i][j] + a[i][k] * b[k][j]) % m;
            }
        }
    }
    return result;
}

std::vector<std::vector<long long>> matrixPower(std::vector<std::vector<long long>> matrix, long long n, long long m) {
    if (n == 0) {
        return {{1, 0}, {0, 1}};
    }
    if (n == 1) {
        return matrix;
    }

    auto halfPower = matrixPower(matrix, n / 2, m);
    auto square = matrixMultiply(halfPower, halfPower, m);

    if (n % 2 == 0) {
        return square;
    } else {
        return matrixMultiply(matrix, square, m);
    }
}

// O(log N) time complexity
long long get_fibonacci_huge(long long n, long long m) {
    if (n <= 1)
        return n;

    std::vector<std::vector<long long>> initialMatrix = {{1, 1}, {1, 0}};
    auto resultMatrix = matrixPower(initialMatrix, n - 1, m);
    return resultMatrix[0][0];
}

// O(N) time complexity
long long get_fibonacci_huge_naive1_functional(long long n, long long m) {
    // Helper function for recursive calculation
    std::function<long long(long long, long long, long long)> fib_helper = [&](long long n, long long prev, long long curr) -> long long {
        if (n == 0)
            return prev;
        return fib_helper(n - 1, curr, (prev + curr) % m);
    };
    return fib_helper(n, 0, 1);
}

// O(N) time complexity
long long get_fibonacci_huge_naive2_functional(long long n, long long m) {
    // Function to get the Pisano period
    std::function<long long(long long)> getPisanoPeriod = [&](long long m) -> long long {
        long long previous = 0, current = 1;
        for (long long i = 0; i < m * m; i++) {
            long long temp = current;
            current = (previous + current) % m;
            previous = temp;
            if (previous == 0 && current == 1)
                return i + 1;
        }
        return 0; // Should not happen
    };

    // Reducing n to within the Pisano period
    n = n % getPisanoPeriod(m);
    return get_fibonacci_huge_naive1_functional(n, m);
}

// O(log N) time complexity
long long get_fibonacci_huge_functional(long long n, long long m) {
    return get_fibonacci_huge_naive1_functional(n, m); // As the efficient solution is already functional
}

int main() {
    long long n, m;
    std::cin >> n >> m;
    std::cout << get_fibonacci_huge(n, m) << '\n';
}

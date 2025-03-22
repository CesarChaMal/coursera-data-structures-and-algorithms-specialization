#include <iostream>
#include <vector>
#include <functional>

// O(N) time complexity
long long get_fibonacci_huge_naive(long long n, long long m) {
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

int main() {
    long long n, m;
    std::cin >> n >> m;
    std::cout << get_fibonacci_huge(n, m) << '\n';
}

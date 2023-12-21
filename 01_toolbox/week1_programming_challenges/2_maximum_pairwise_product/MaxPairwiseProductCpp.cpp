#include <iostream>
#include <vector>
#include <algorithm>
#include <limits>

// O(n^2) time complexity
int MaxPairwiseProductNaive(const std::vector<int>& numbers) {
    int max_product = 0;
    int n = numbers.size();

    for (int first = 0; first < n; ++first) {
        for (int second = first + 1; second < n; ++second) {
            max_product = std::max(max_product,
                numbers[first] * numbers[second]);
        }
    }

    return max_product;
}

// O(n) time complexity
int MaxPairwiseProduct(const std::vector<int>& numbers) {
    int n = numbers.size();
    if (n < 2) return 0;

    int max1 = std::max(numbers[0], numbers[1]);
    int max2 = std::min(numbers[0], numbers[1]);

    for (int i = 2; i < n; ++i) {
        if (numbers[i] > max1) {
            max2 = max1;
            max1 = numbers[i];
        } else if (numbers[i] > max2) {
            max2 = numbers[i];
        }
    }

    return max1 * max2;
}

// O(n log n) time complexity, functional approach
long long maxPairwiseProductFunctionalNaive(const std::vector<int>& numbers) {
    if (numbers.size() < 2) return 0;

    std::vector<int> sorted_numbers = numbers;
    std::sort(sorted_numbers.begin(), sorted_numbers.end(), std::greater<int>());

    return static_cast<long long>(sorted_numbers[0]) * sorted_numbers[1];
}

// O(n) time complexity, functional approach
long long maxPairwiseProductFunctional(const std::vector<int>& numbers) {
    long long max1 = std::numeric_limits<long long>::min();
    long long max2 = std::numeric_limits<long long>::min();

    for (int number : numbers) {
        if (number > max1) {
            max2 = max1;
            max1 = number;
        } else if (number > max2) {
            max2 = number;
        }
    }

    return max1 * max2;
}

int main() {
    int n;
    std::cin >> n;
    std::vector<int> numbers(n);
    for (int i = 0; i < n; ++i) {
        std::cin >> numbers[i];
    }

    std::cout << MaxPairwiseProduct(numbers) << "\n";
    return 0;
}

#include <iostream>
#include <vector>
#include <algorithm>
#include <limits>
#include <random>

// O(n^2) time complexity
long long MaxPairwiseProductNaive(const std::vector<int>& numbers) {
    long long max_product = 0;
    long long n = numbers.size();

    for (int first = 0; first < n; ++first) {
        for (int second = first + 1; second < n; ++second) {
            max_product = std::max(max_product, static_cast<long long>(numbers[first]) * numbers[second]);
        }
    }

    return max_product;
}

// O(n) time complexity
long long MaxPairwiseProduct(const std::vector<int>& numbers) {
    int n = numbers.size();
    if (n < 2) return 0;

    long long max1 = std::max(numbers[0], numbers[1]);
    long long max2 = std::min(numbers[0], numbers[1]);

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

bool CompareStrategies(const std::vector<int>& numbers) {
    int resultNaive = MaxPairwiseProductNaive(numbers);
    int resultEfficient = MaxPairwiseProduct(numbers);

    if (resultNaive != resultEfficient) {
        std::cout << "Discrepancy found: Naive(" << resultNaive << ") != Efficient(" << resultEfficient << ")\n";
        return false;
    }
    return true;
}

void StressTest(int maxIterations, int arraySize, int maxValue) {
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> dis(2, arraySize);
    std::uniform_int_distribution<> val_dis(0, maxValue);

    for (int i = 0; i < maxIterations; ++i) {
        int n = dis(gen);
        std::vector<int> numbers(n);
        for (int &num : numbers) {
            num = val_dis(gen);
        }

        // Compare the results of different methods
//        long long resultNaive = MaxPairwiseProductNaive(numbers);
//        long long resultEfficient = MaxPairwiseProduct(numbers);
//        long long resultFunctionalNaive = maxPairwiseProductFunctionalNaive(numbers);
//        long long resultFunctional = maxPairwiseProductFunctional(numbers);
//
//        if (resultNaive != resultEfficient ||
//            resultNaive != resultFunctionalNaive ||
//            resultNaive != resultFunctional) {
//            std::cout << "Mismatch found:" << std::endl;
//            std::cout << "Naive: " << resultNaive << std::endl;
//            std::cout << "Efficient: " << resultEfficient << std::endl;
//            std::cout << "Functional Naive: " << resultFunctionalNaive << std::endl;
//            std::cout << "Functional: " << resultFunctional << std::endl;
//
//            // Print the numbers for debugging
//            for (int num : numbers) {
//                std::cout << num << " ";
//            }
//            std::cout << std::endl;
//            return;
//        }

        std::cout << "Iteration " << i + 1 << ": Array size - " << n << "\n";
        std::cout << "Array: ";
        for (const auto& num : numbers) {
            std::cout << num << " ";
        }
        std::cout << "\n";

        if (!CompareStrategies(numbers)) {
            std::cout << "Mismatch found in iteration " << i + 1 << "\n";
            break;
        }
    }
    std::cout << "All tests passed." << std::endl;
}

int main() {
    // Uncomment to run stress test
    StressTest(100000, 1000, 100000);

    // Uncomment to run with user input
    /*
    int n;
    std::cin >> n;
    std::vector<int> numbers(n);
    for (int i = 0; i < n; ++i) {
        std::cin >> numbers[i];
    }
    std::cout << MaxPairwiseProduct(numbers) << "\n";
    */
    return 0;
}
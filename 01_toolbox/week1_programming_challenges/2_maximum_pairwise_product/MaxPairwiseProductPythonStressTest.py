from functools import reduce
import random

# O(n^2) time complexity
def maxPairwiseProductNaive(numbers):
    n = len(numbers)
    max_product = 0
    for first in range(n):
        for second in range(first + 1, n):
            max_product = max(max_product, numbers[first] * numbers[second])
    return max_product

# O(n) time complexity
def maxPairwiseProduct(numbers):
    if len(numbers) < 2:
        return 0

    max1 = max(numbers[0], numbers[1])
    max2 = min(numbers[0], numbers[1])

    for number in numbers[2:]:
        if number > max1:
            max2, max1 = max1, number
        elif number > max2:
            max2 = number

    return max1 * max2

# O(n log n) time complexity, functional approach
def maxPairwiseProductFunctionalNaive1(numbers):
    sorted_numbers = sorted(numbers, reverse=True)
    return sorted_numbers[0] * sorted_numbers[1]

# O(n log n) time complexity, functional approach
def maxPairwiseProductFunctionalNaive2(numbers):
    top_two = sorted(numbers, reverse=True)[:2]
    return reduce(lambda x, y: x * y, top_two)

# O(n) time complexity, functional approach
def maxPairwiseProductFunctional(numbers):
    def update_maxes(maxes, current):
        first, second = maxes
        if current > first:
            return (current, first)
        elif current > second:
            return (first, current)
        else:
            return maxes

    if len(numbers) < 2:
        return 0

    initial_maxes = (max(numbers[0], numbers[1]), min(numbers[0], numbers[1]))
    first, second = reduce(update_maxes, numbers[2:], initial_maxes)
    return first * second

# if __name__ == '__main__':
#     _ = int(input())
#     input_numbers = list(map(int, input().split()))
#     print(maxPairwiseProductFunctionalNaive2(input_numbers))

def compare_strategies(numbers, strategy1, strategy2, description):
    result1 = strategy1(numbers)
    result2 = strategy2(numbers)
    if result1 != result2:
        print(f"Discrepancy found in {description}: {result1} != {result2}")
        return False
    return True

def stress_test(max_iterations=100000, array_size=1000, max_value=100000):
    for iteration in range(1, max_iterations + 1):
        numbers = [random.randint(0, max_value) for _ in range(random.randint(2, array_size))]

        print(f"Iteration {iteration}: Array size - {len(numbers)}")
        print("Array:", numbers)

        # result_naive = maxPairwiseProductNaive(numbers)
        # result_efficient = maxPairwiseProduct(numbers)
        # result_functional_naive1 = maxPairwiseProductFunctionalNaive1(numbers)
        # result_functional_naive2 = maxPairwiseProductFunctionalNaive2(numbers)
        # result_functional = maxPairwiseProductFunctional(numbers)
        #
        # if all(result == result_efficient for result in [result_naive, result_functional_naive1, result_functional_naive2, result_functional]):
        #     print("All methods agree.")
        # else:
        #     print("Discrepancy found:")
        #     print("Naive:", result_naive)
        #     print("Efficient:", result_efficient)
        #     print("Functional Naive 1:", result_functional_naive1)
        #     print("Functional Naive 2:", result_functional_naive2)
        #     print("Functional:", result_functional)
        #     break

        strategies_to_compare = [
            (maxPairwiseProductNaive, "Naive"),
            (maxPairwiseProductFunctional, "Functional"),
            (maxPairwiseProductFunctionalNaive1, "Functional Naive 1"),
            (maxPairwiseProductFunctionalNaive2, "Functional Naive 2")
        ]

        efficient_result = maxPairwiseProduct(numbers)
        all_match = True
        for strategy, description in strategies_to_compare:
            if not compare_strategies(numbers, strategy, lambda x: efficient_result, description):
                all_match = False
                break

        if all_match:
            print("All methods agree.")
        else:
            break

    else:
        print("All tests passed successfully.")

if __name__ == '__main__':
    stress_test()

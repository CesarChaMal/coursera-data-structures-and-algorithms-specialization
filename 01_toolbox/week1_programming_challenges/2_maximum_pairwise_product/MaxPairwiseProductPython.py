from functools import reduce

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

if __name__ == '__main__':
    _ = int(input())
    input_numbers = list(map(int, input().split()))
    print(maxPairwiseProductFunctionalNaive2(input_numbers))

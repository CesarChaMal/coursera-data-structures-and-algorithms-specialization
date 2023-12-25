def get_fibonacci_last_digit_naive(n):
    """O(n) time complexity - Naive Iterative Approach"""
    if n <= 1:
        return n
    prev, current = 0, 1
    for _ in range(2, n + 1):
        tmp_previous = prev
        prev = current
        current = tmp_previous + current
    return current % 10


def get_fibonacci_last_digit(n):
    """O(n) time complexity - Efficient Iterative Approach"""
    if n <= 1:
        return n
    prev, current = 0, 1
    for _ in range(2, n + 1):
        prev, current = current, (prev + current) % 10
    return current


def get_fibonacci_last_digit_functional_naive(n):
    """O(n) time complexity, functional naive approach"""

    def fib(n, a=0, b=1):
        return a if n == 0 else fib(n - 1, b, (a + b) % 10)

    return fib(n)


def get_fibonacci_last_digit_functional(n):
    """O(n) time complexity, functional approach"""
    fib_sequence = ((0, 1), (1, 0))
    fib = (sum(pair) % 10 for pair in fib_sequence)
    for _ in range(n):
        next(fib)
    return next(fib)


# Function to compare two strategies
def compare_strategies(n, *strategies):
    results = [strategy(n) for strategy in strategies]
    if len(set(results)) != 1:
        print(f"Discrepancy found at n={n}: {results}")
        return False
    return True


# Stress Test Function
def stress_test(max_iterations, max_n, *strategies):
    import random
    for iteration in range(max_iterations):
        n = random.randint(0, max_n)
        print(f"Testing with n = {n}")
        if not compare_strategies(n, *strategies):
            return
        print("OK")
    # import random
    # for iteration in range(max_iterations):
    #     n = random.randint(0, max_n)
    #
    #     print(f"Testing with n = {n}")
    #
    #     naive = fibonacci_number_naive(n)
    #     efficient = fibonacci_number(n)
    #     functional_naive = fibonacci_number_functional_naive(n)
    #     functional = fibonacci_number_functional(n)
    #
    #     if naive != efficient or naive != functional_naive or naive != functional:
    #         print(f"Discrepancy found: Naive({naive}), Efficient({efficient}), "
    #               f"Functional Naive({functional_naive}), Functional({functional})")
    #         return
    #
    #     print("OK")


if __name__ == '__main__':
    # Uncomment to run stress test
    stress_test(100000, 40, get_fibonacci_last_digit_naive, get_fibonacci_last_digit,
                get_fibonacci_last_digit_functional_naive, get_fibonacci_last_digit_functional)

    # Uncomment to run with user input
    # input_n = int(input())
    # print(get_fibonacci_last_digit(input_n))

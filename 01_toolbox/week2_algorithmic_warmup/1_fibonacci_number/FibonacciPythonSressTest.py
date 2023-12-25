def fibonacci_number_naive(n):
    """O(2^n) time complexity"""
    if n <= 1:
        return n
    return fibonacci_number_naive(n - 1) + fibonacci_number_naive(n - 2)


def fibonacci_number(n):
    """O(n) time complexity"""
    if n <= 1:
        return n
    prev, current = 0, 1
    for _ in range(2, n + 1):
        prev, current = current, prev + current
    return current


def fibonacci_number_functional_naive(n):
    """O(2^n) time complexity, functional naive approach"""

    def fib_helper(n, a=0, b=1):
        return a if n == 0 else fib_helper(n - 1, b, a + b)

    return fib_helper(n)


def fibonacci_number_functional(n):
    """O(n) time complexity, functional approach"""
    from itertools import islice

    def fib_sequence():
        a, b = 0, 1
        while True:
            yield a
            a, b = b, a + b

    return next(islice(fib_sequence(), n, None))


def compare_strategies(n, *strategies):
    results = [strategy(n) for strategy in strategies]
    if len(set(results)) != 1:
        print(f"Discrepancy found at n={n}: {results}")
        return False
    return True


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
    stress_test(100000, 40, fibonacci_number_naive, fibonacci_number, fibonacci_number_functional_naive,
                fibonacci_number_functional)

    # Uncomment to run with user input
    # input_n = int(input())
    # print(fibonacci_number(input_n))

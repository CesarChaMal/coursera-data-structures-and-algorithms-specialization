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

if __name__ == '__main__':
    input_n = int(input())
    print(fibonacci_number(input_n))

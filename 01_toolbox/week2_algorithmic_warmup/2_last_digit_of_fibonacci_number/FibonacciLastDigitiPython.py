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

if __name__ == '__main__':
    input_n = int(input())
    print(get_fibonacci_last_digit(input_n))

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


if __name__ == '__main__':
    input_n = int(input())
    print(fibonacci_number(input_n))

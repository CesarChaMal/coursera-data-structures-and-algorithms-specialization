def fibonacci_huge_naive1(n, m):
    """O(N) time complexity."""
    if n <= 1:
        return n

    previous = 0
    current  = 1

    for _ in range(n - 1):
        previous, current = current, previous + current

    return current % m

def get_pisano_period(m):
    """O(N) time complexity."""
    previous, current = 0, 1
    for i in range(m * m):
        previous, current = current, (previous + current) % m
        if previous == 0 and current == 1:
            return i + 1

def fibonacci_huge_naive2(n, m):
    """O(N) time complexity."""
    pisano_period = get_pisano_period(m)
    n = n % pisano_period

    previous, current = 0, 1
    if n <= 1:
        return n

    for _ in range(n - 1):
        previous, current = current, (previous + current) % m

    return current

def fibonacci_huge_naive3(n, m):
    """O(N) time complexity."""
    if n <= 1:
        return n

    previous, current = 0, 1
    for _ in range(n - 1):
        next = (previous + current) % m
        previous, current = current, next

    return current

def matrix_multiply(a, b, m):
    result = [[0, 0], [0, 0]]
    for i in range(2):
        for j in range(2):
            for k in range(2):
                result[i][j] = (result[i][j] + a[i][k] * b[k][j]) % m
    return result

def matrix_power(matrix, n, m):
    if n == 0:
        return [[1, 0], [0, 1]]
    if n == 1:
        return matrix

    half_power = matrix_power(matrix, n // 2, m)
    square = matrix_multiply(half_power, half_power, m)
    if n % 2 == 0:
        return square
    else:
        return matrix_multiply(matrix, square, m)

def fibonacci_huge(n, m):
    """O(log N) time complexity."""
    if n <= 1:
        return n

    initial_matrix = [[1, 1], [1, 0]]
    result_matrix = matrix_power(initial_matrix, n - 1, m)
    return result_matrix[0][0]

def fib_helper(n, prev, curr, m):
    if n == 0:
        return prev
    return fib_helper(n - 1, curr, (prev + curr) % m, m)

def fibonacci_huge_naive1_functional(n, m):
    """O(N) time complexity - Functional style."""
    return fib_helper(n, 0, 1, m)

def fibonacci_huge_naive2_functional(n, m):
    """O(N) time complexity - Functional style."""
    pisano_period = get_pisano_period(m)
    return fib_helper(n % pisano_period, 0, 1, m)

def fibonacci_huge_functional(n, m):
    """O(log N) time complexity - Functional style."""
    return fibonacci_huge(n, m)  # As it's already implemented in a functional style

if __name__ == '__main__':
    n, m = map(int, input().split())
    print(fibonacci_huge(n, m))

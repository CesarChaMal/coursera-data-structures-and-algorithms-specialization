def gcd(a, b):
    """Euclidean Algorithm for finding GCD."""
    if b == 0:
        return a
    else:
        return gcd(b, a % b)

def lcm(a, b):
    """Efficient computation of LCM using GCD."""
    return (a * b) // gcd(a, b)

def lcm_naive1(a, b):
    """Naive approach to find LCM."""
    for l in range(1, a * b + 1):
        if l % a == 0 and l % b == 0:
            return l
    return a * b  # Fallback return, theoretically unreachable

def lcm_naive2(a, b):
    """Alternative naive approach for LCM."""
    lcm = 1
    while True:
        if lcm % a == 0 and lcm % b == 0:
            return lcm
        lcm += 1

def lcm_naive_functional(a, b):
    """Functional approach for naive LCM computation using generator."""
    limit = a * b

    def lcm_generator():
        for current in range(1, limit + 1):
            yield current

    for current in lcm_generator():
        if current % a == 0 and current % b == 0:
            return current

    return limit

def lcm_functional(a, b):
    """Functional approach for efficient LCM computation."""
    def euclid_gcd(x, y):
        return x if y == 0 else euclid_gcd(y, x % y)

    return (a * b) // euclid_gcd(a, b)

def compare_strategies(a, b, *strategies):
    results = [strategy(a, b) for strategy in strategies]
    if len(set(results)) != 1:
        print(f"Discrepancy found at a={a}, b={b}: {results}")
        return False
    return True

def stress_test(max_iterations, max_n, *strategies):
    import random
    for iteration in range(max_iterations):
        a = random.randint(1, max_n)
        b = random.randint(1, max_n)
        print(f"Testing with a = {a}, b = {b}")
        if not compare_strategies(a, b, *strategies):
            return
        print("OK")

if __name__ == '__main__':
    # Uncomment to run stress test
    stress_test(100000, 1000000, lcm_naive1, lcm_naive2, lcm, lcm_naive_functional, lcm_functional)

def gcd_naive(a, b):
    """O(min(a, b)) time complexity - Naive Iterative Approach"""
    current_gcd = 1
    for d in range(2, min(a, b) + 1):
        if a % d == 0 and b % d == 0:
            if d > current_gcd:
                current_gcd = d

    return current_gcd


def gcd(a, b):
    """O(log(min(a, b))) time complexity - Euclidean Algorithm"""
    return a if b == 0 else gcd(b, a % b)

def gcd_functional_naive(a, b):
    """O(min(a, b)) time complexity - Naive Functional Approach"""
    from functools import reduce

    def update_gcd(current_gcd, d):
        if a % d == 0 and b % d == 0:
            return d
        return current_gcd

    return reduce(update_gcd, range(2, min(a, b) + 1), 1)


def gcd_functional(a, b):
    """O(log(min(a, b))) time complexity - Euclidean Algorithm, Functional Approach"""
    from functools import reduce

    # Create a sequence of steps for the Euclidean algorithm
    def steps(seq, _):
        x, y = seq[-1]
        return seq + [(y, x % y)] if y != 0 else seq

    # Apply the steps until the second element of the tuple becomes 0
    gcd_steps = reduce(steps, range(max(a, b)), [(a, b)])

    # The first element of the last tuple is the gcd
    return gcd_steps[-1][0]



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
    stress_test(100000, 1000000, gcd_naive, gcd, gcd_functional_naive, gcd_functional)

    # Uncomment to run with user input
    # a, b = map(int, input().split())
    # print(gcd_naive(a, b))

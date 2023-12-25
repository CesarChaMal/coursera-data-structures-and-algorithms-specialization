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

if __name__ == "__main__":
    a, b = map(int, input().split())
    print(gcd(a, b))

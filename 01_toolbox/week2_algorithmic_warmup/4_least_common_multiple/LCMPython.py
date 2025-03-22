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
    """Functional approach for naive LCM computation."""
    def find_lcm(current, limit):
        if current > limit:
            return limit
        if current % a == 0 and current % b == 0:
            return current
        return find_lcm(current + 1, limit)

    return find_lcm(1, a * b)

def lcm_functional(a, b):
    """Functional approach for efficient LCM computation."""
    def euclid_gcd(x, y):
        return x if y == 0 else euclid_gcd(y, x % y)

    return (a * b) // euclid_gcd(a, b)

if __name__ == '__main__':
    a, b = map(int, input().split())
    print(lcm(a, b))


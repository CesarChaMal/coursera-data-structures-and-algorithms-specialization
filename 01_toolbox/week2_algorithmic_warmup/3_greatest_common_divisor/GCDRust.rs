use std::io;

/// O(min(a, b)) naive approach
fn gcd_naive(a: u64, b: u64) -> u64 {
    let mut current_gcd = 1;
    for d in 2..=a.min(b) {
        if a % d == 0 && b % d == 0 {
            current_gcd = d;
        }
    }
    current_gcd
}

/// Euclidean algorithm - O(log(min(a, b)))
fn gcd(a: u64, b: u64) -> u64 {
    if b == 0 {
        a
    } else {
        gcd(b, a % b)
    }
}

/// Naive functional approach
fn gcd_functional_naive(a: u64, b: u64) -> u64 {
    (2..=a.min(b))
        .filter(|&d| a % d == 0 && b % d == 0)
        .max()
        .unwrap_or(1)
}

/// Euclidean algorithm functional
fn gcd_functional(a: u64, b: u64) -> u64 {
    let gcd_fn: &dyn Fn(u64, u64) -> u64 = &|a, b| if b == 0 { a } else { gcd_fn(b, a % b) };
    gcd_fn(a, b)
}

fn main() {
    let mut input = String::new();
    io::stdin().read_line(&mut input).unwrap();
    let parts: Vec<u64> = input
        .trim()
        .split_whitespace()
        .map(|x| x.parse().unwrap())
        .collect();

    println!("{}", gcd(parts[0], parts[1]));
}

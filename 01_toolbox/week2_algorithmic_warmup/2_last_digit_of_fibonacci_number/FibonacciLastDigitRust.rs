use std::io;

/// Naive O(n)
fn get_fibonacci_last_digit_naive(n: u32) -> u32 {
    if n <= 1 {
        return n;
    }

    let mut prev = 0;
    let mut curr = 1;
    for _ in 2..=n {
        let tmp = prev;
        prev = curr;
        curr = tmp + curr;
    }
    curr % 10
}

/// Optimized O(n)
fn get_fibonacci_last_digit(n: u32) -> u32 {
    if n <= 1 {
        return n;
    }

    let mut prev = 0;
    let mut curr = 1;
    for _ in 2..=n {
        let tmp = prev;
        prev = curr;
        curr = (tmp + curr) % 10;
    }
    curr
}

/// Functional-style naive O(n)
fn get_fibonacci_last_digit_functional_naive(n: u32) -> u32 {
    (0..=n).fold((0u32, 1u32), |(a, b), _| (b, a + b)).0 % 10
}

/// Functional-style optimized O(n)
fn get_fibonacci_last_digit_functional(n: u32) -> u32 {
    (0..=n).fold((0u32, 1u32), |(a, b), _| (b, (a + b) % 10)).0
}

fn main() {
    let mut input = String::new();
    io::stdin().read_line(&mut input).unwrap();
    let n: u32 = input.trim().parse().unwrap();

    println!("{}", get_fibonacci_last_digit(n));
}

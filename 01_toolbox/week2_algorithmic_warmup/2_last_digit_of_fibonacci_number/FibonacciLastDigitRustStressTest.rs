// to run change the name to main.rs and place it in src folder
// run --package coursera-data-structures-and-algorithms-specialization --bin coursera-data-structures-and-algorithms-specialization --release

use rand::Rng;

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

type Strategy = fn(u32) -> u32;

fn compare_strategies(f1: Strategy, f2: Strategy, n: u32) -> bool {
    let r1 = f1(n);
    let r2 = f2(n);
    if r1 != r2 {
        println!("Discrepancy at n = {}: {} != {}", n, r1, r2);
        false
    } else {
        true
    }
}

fn main() {
    let mut rng = rand::thread_rng();
    for i in 0..100_000 {
        let n = rng.gen_range(0..1000);
        println!("Testing with n = {}", n);

        let naive = get_fibonacci_last_digit_naive as Strategy;
        let efficient = get_fibonacci_last_digit as Strategy;
        let functional_naive = get_fibonacci_last_digit_functional_naive as Strategy;
        let functional = get_fibonacci_last_digit_functional as Strategy;

        if !compare_strategies(naive, efficient, n) ||
           !compare_strategies(naive, functional_naive, n) ||
           !compare_strategies(naive, functional, n) {
            println!("Test failed");
            break;
        }

        println!("OK");
    }
}

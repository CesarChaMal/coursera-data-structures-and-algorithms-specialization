use std::io;

/// Naive O(2^n)
fn calc_fib_naive(n: u32) -> u64 {
    if n <= 1 {
        return n as u64;
    }
    calc_fib_naive(n - 1) + calc_fib_naive(n - 2)
}

/// Optimized O(n)
fn calc_fib(n: u32) -> u64 {
    if n <= 1 {
        return n as u64;
    }

    let mut prev = 0;
    let mut curr = 1;

    for _ in 2..=n {
        let new_curr = prev + curr;
        prev = curr;
        curr = new_curr;
    }

    curr
}

/// Functional-style O(n) using iterator
fn calc_fib_functional(n: u32) -> u64 {
    (0..=n)
        .fold((0u64, 1u64), |(a, b), _| (b, a + b))
        .0
}

/// Naive functional recursion using Y combinator pattern
fn calc_fib_functional_naive(n: u32) -> u64 {
    fn y_combinator<T, R>(f: impl Fn(&dyn Fn(T) -> R, T) -> R) -> impl Fn(T) -> R
    where
        T: Copy,
    {
        move |x| f(&|y| y_combinator(&f)(y), x)
    }

    let fib = y_combinator(|fib, n| {
        if n <= 1 {
            n as u64
        } else {
            fib(n - 1) + fib(n - 2)
        }
    });

    fib(n)
}

fn main() {
    let mut input = String::new();
    io::stdin().read_line(&mut input).unwrap();
    let n: u32 = input.trim().parse().unwrap();

    println!("{}", calc_fib(n));
}

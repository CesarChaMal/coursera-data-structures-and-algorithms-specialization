#![recursion_limit = "512"]
use rand::Rng;

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

/// Functional-style O(n)
fn calc_fib_functional(n: u32) -> u64 {
    (0..=n)
        .fold((0u64, 1u64), |(a, b), _| (b, a + b))
        .0
}

/// Naive functional recursive using boxed closure to avoid compile-time recursion limit
fn calc_fib_functional_naive(n: u32) -> u64 {
    fn y_combinator<T: Copy + 'static, R: 'static>(
        f: impl Fn(&dyn Fn(T) -> R, T) -> R + 'static,
    ) -> Box<dyn Fn(T) -> R> {
        let f = std::rc::Rc::new(f);
        fn wrap<T: Copy + 'static, R: 'static>(
            f: std::rc::Rc<impl Fn(&dyn Fn(T) -> R, T) -> R + 'static>,
        ) -> Box<dyn Fn(T) -> R> {
            Box::new(move |x| f(&*wrap(f.clone()), x))
        }
        wrap(f)
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

type FibonacciStrategy = fn(u32) -> u64;

fn compare_strategies(f1: FibonacciStrategy, f2: FibonacciStrategy, n: u32) -> bool {
    let r1 = f1(n);
    let r2 = f2(n);
    if r1 != r2 {
        println!("❌ Discrepancy for n = {}: {} != {}", n, r1, r2);
        false
    } else {
        true
    }
}

fn main() {
    let mut rng = rand::thread_rng();
    let max_iterations = 100_000;

    for iter in 1..=max_iterations {
        let n = rng.gen_range(0..40);
        println!("Testing with n = {}", n);

        let naive = calc_fib_naive as FibonacciStrategy;
        let efficient = calc_fib as FibonacciStrategy;
        let functional = calc_fib_functional as FibonacciStrategy;
        let functional_naive = calc_fib_functional_naive as FibonacciStrategy;

        if !compare_strategies(naive, efficient, n) {
            println!("❌ Naive vs Efficient failed");
            break;
        }
        if !compare_strategies(functional_naive, efficient, n) {
            println!("❌ Functional Naive vs Efficient failed");
            break;
        }
        if !compare_strategies(functional, efficient, n) {
            println!("❌ Functional vs Efficient failed");
            break;
        }

        println!("✅ OK");
    }

    println!("🎉 All tests passed");
}

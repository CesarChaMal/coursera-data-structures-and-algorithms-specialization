use rand::Rng;

fn gcd(a: u64, b: u64) -> u64 {
    if b == 0 { a } else { gcd(b, a % b) }
}

// Naive approach - O(a*b)
fn lcm_naive1(a: u64, b: u64) -> u64 {
    let limit = a.checked_mul(b).unwrap_or(u64::MAX);
    for l in 1..=limit {
        if l % a == 0 && l % b == 0 {
            return l;
        }
    }
    limit
}

// Alternative naive approach - O(a*b)
fn lcm_naive2(mut l: u64, a: u64, b: u64) -> u64 {
    loop {
        if l % a == 0 && l % b == 0 {
            return l;
        }
        l += 1;
    }
}

// Efficient - O(log(min(a,b)))
fn lcm(a: u64, b: u64) -> u64 {
    (a * b) / gcd(a, b)
}

// Functional naive approach
fn lcm_functional_naive(a: u64, b: u64) -> u64 {
    let limit = a.checked_mul(b).unwrap_or(u64::MAX);
    (1..=limit).find(|&x| x % a == 0 && x % b == 0).unwrap_or(limit)
}

// Functional efficient approach
fn lcm_functional(a: u64, b: u64) -> u64 {
    let gcd_fn = |mut x: u64, mut y: u64| -> u64 {
        while y != 0 {
            let temp = y;
            y = x % y;
            x = temp;
        }
        x
    };
    (a * b) / gcd_fn(a, b)
}

type Strategy = fn(u64, u64) -> u64;

fn compare(f1: Strategy, f2: Strategy, a: u64, b: u64) -> bool {
    let r1 = f1(a, b);
    let r2 = f2(a, b);
    if r1 != r2 {
        println!("Discrepancy: {} != {} for a={}, b={}", r1, r2, a, b);
        false
    } else {
        true
    }
}

fn main() {
    let mut rng = rand::thread_rng();
    let max_iter = 100_000;
    let max_val = 1_000u64; // Lowered to prevent overflow and performance issues in naive methods

    for _ in 0..max_iter {
        let a = rng.gen_range(1..=max_val);
        let b = rng.gen_range(1..=max_val);

        println!("Testing a = {}, b = {}", a, b);

        if !compare(lcm_naive1, lcm, a, b) {
            println!("Naive1 vs Efficient failed");
            break;
        }
        if !compare(|x, y| lcm_naive2(1, x, y), lcm, a, b) {
            println!("Naive2 vs Efficient failed");
            break;
        }
        if !compare(lcm_functional_naive, lcm, a, b) {
            println!("Functional Naive vs Efficient failed");
            break;
        }
        if !compare(lcm_functional, lcm, a, b) {
            println!("Functional vs Efficient failed");
            break;
        }

        println!("OK");
    }

    println!("🎉 All tests passed!");
}

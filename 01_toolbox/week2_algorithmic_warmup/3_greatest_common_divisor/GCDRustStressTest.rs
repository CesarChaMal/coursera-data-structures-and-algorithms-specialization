use rand::Rng;

fn gcd_naive(a: u64, b: u64) -> u64 {
    let mut current_gcd = 1;
    for d in 2..=a.min(b) {
        if a % d == 0 && b % d == 0 {
            current_gcd = d;
        }
    }
    current_gcd
}

fn gcd(a: u64, b: u64) -> u64 {
    if b == 0 { a } else { gcd(b, a % b) }
}

fn gcd_functional_naive(a: u64, b: u64) -> u64 {
    (2..=a.min(b))
        .filter(|&d| a % d == 0 && b % d == 0)
        .max()
        .unwrap_or(1)
}

fn gcd_functional(a: u64, b: u64) -> u64 {
    fn recurse(a: u64, b: u64) -> u64 {
        if b == 0 { a } else { recurse(b, a % b) }
    }
    recurse(a, b)
}

type GCDStrategy = fn(u64, u64) -> u64;

fn compare_strategies(f1: GCDStrategy, f2: GCDStrategy, a: u64, b: u64) -> bool {
    let r1 = f1(a, b);
    let r2 = f2(a, b);
    if r1 != r2 {
        println!("Discrepancy found: {} != {} (a={}, b={})", r1, r2, a, b);
        false
    } else {
        true
    }
}

fn main() {
    let mut rng = rand::thread_rng();
    let max_iterations = 100_000;
    let max_range = 100_000_000u64;

    for _ in 0..max_iterations {
        let a = rng.gen_range(1..=max_range);
        let b = rng.gen_range(1..=max_range);

        println!("Testing with a = {}, b = {}", a, b);

        let naive = gcd_naive as GCDStrategy;
        let efficient = gcd as GCDStrategy;
        let functional_naive = gcd_functional_naive as GCDStrategy;
        let functional = gcd_functional as GCDStrategy;

        if !compare_strategies(naive, efficient, a, b) ||
            !compare_strategies(functional_naive, efficient, a, b) ||
            !compare_strategies(functional, efficient, a, b) {
            println!("❌ Test failed");
            break;
        }

        println!("✅ OK");
    }
}

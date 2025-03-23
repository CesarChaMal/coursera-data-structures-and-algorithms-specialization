use rand::Rng;
use std::time::Duration;

/// Naive O(n²)
fn get_max_pairwise_product_naive(numbers: &[i64]) -> i64 {
    let mut max_product = 0;
    let n = numbers.len();

    for i in 0..n {
        for j in (i + 1)..n {
            max_product = max_product.max(numbers[i] * numbers[j]);
        }
    }

    max_product
}

/// Optimized O(n)
fn get_max_pairwise_product(numbers: &[i64]) -> i64 {
    let mut max1 = numbers[0];
    let mut max2 = numbers[1];

    if max2 > max1 {
        std::mem::swap(&mut max1, &mut max2);
    }

    for &num in &numbers[2..] {
        if num > max1 {
            max2 = max1;
            max1 = num;
        } else if num > max2 {
            max2 = num;
        }
    }

    max1 * max2
}

fn main() {
    let max_iterations = 100_000;
    let mut rng = rand::thread_rng();

    for iter in 1..=max_iterations {
        let n = rng.gen_range(2..100); // change to 1000 for more stress
        let numbers: Vec<i64> = (0..n).map(|_| rng.gen_range(0..100_000)).collect();

        let result_naive = get_max_pairwise_product_naive(&numbers);
        let result_optimized = get_max_pairwise_product(&numbers);

        if result_naive != result_optimized {
            println!("❌ Discrepancy found!");
            println!("Input: {:?}", numbers);
            println!("Naive result: {}", result_naive);
            println!("Optimized result: {}", result_optimized);
            break;
        } else {
            println!("✅ Iteration {} passed", iter);
        }
    }
}

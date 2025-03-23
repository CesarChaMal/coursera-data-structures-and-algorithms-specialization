use std::io::{self, BufRead};

/// Naive O(n²) approach
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

/// Optimized O(n) approach
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
    let stdin = io::stdin();
    let mut lines = stdin.lock().lines();

    // Read n
    let n: usize = lines.next().unwrap().unwrap().trim().parse().unwrap();

    // Read the numbers
    let numbers_line = lines.next().unwrap().unwrap();
    let numbers: Vec<i64> = numbers_line
        .split_whitespace()
        .take(n)
        .map(|x| x.parse().unwrap())
        .collect();

    println!("{}", get_max_pairwise_product(&numbers));
}

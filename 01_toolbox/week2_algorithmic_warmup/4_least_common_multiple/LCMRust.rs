use std::io;

fn gcd(a: u64, b: u64) -> u64 {
    if b == 0 { a } else { gcd(b, a % b) }
}

fn lcm(a: u64, b: u64) -> u64 {
    (a * b) / gcd(a, b)
}

fn main() {
    let mut input = String::new();
    std::io::stdin().read_line(&mut input).unwrap();
    let numbers: Vec<u64> = input
        .trim()
        .split_whitespace()
        .map(|n| n.parse().unwrap())
        .collect();

    if numbers.len() < 2 {
        eprintln!("Please provide two numbers.");
        return;
    }

    println!("{}", lcm(numbers[0], numbers[1]));
}

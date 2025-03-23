use std::io;

fn sum_of_two_digits(first_digit: i32, second_digit: i32) -> i32 {
    first_digit + second_digit
}

fn main() {
    let mut input = String::new();
    io::stdin().read_line(&mut input).unwrap();

    let nums: Vec<i32> = input
        .trim()
        .split_whitespace()
        .map(|x| x.parse::<i32>().unwrap())
        .collect();

    if nums.len() == 2 {
        println!("{}", sum_of_two_digits(nums[0], nums[1]));
    } else {
        println!("Please enter exactly two integers.");
    }
}

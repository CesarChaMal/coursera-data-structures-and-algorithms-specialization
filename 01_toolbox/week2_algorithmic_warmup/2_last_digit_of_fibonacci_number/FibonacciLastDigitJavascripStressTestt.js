function readInput() {
    const readline = require('readline');
    const rl = readline.createInterface({
        input: process.stdin,
        terminal: false
    });

    rl.on('line', line => {
        console.log(getFibonacciLastDigit(parseInt(line, 10)));
        process.exit();
    });
}

// O(n) time complexity - Efficient Iterative Approach
function getFibonacciLastDigit(n) {
    if (n <= 1) return n;
    let prev = 0, current = 1;
    for (let i = 2; i <= n; i++) {
        [prev, current] = [current, (prev + current) % 10];
    }
    return current;
}

// O(n) time complexity - Naive Iterative Approach
function getFibonacciLastDigitNaive(n) {
    if (n <= 1) return n;
    let prev = 0, current = 1;
    for (let i = 2; i <= n; i++) {
        let tmp_previous = prev;
        prev = current;
        current = (tmp_previous + current) % 10;
    }
    return current;
}

// O(n) time complexity, functional naive approach
function getFibonacciLastDigitFunctionalNaive(n) {
    const fib = (n, a = 0, b = 1) => n === 0 ? a : fib(n - 1, b, (a + b) % 10);
    return fib(n);
}

// O(n) time complexity, functional approach
function getFibonacciLastDigitFunctional(n) {
    if (n === 0) return 0;

    return Array.from({length: n - 1}) // Adjust the length to correctly handle indexing
        .reduce(([prev, curr]) => [curr, (prev + curr) % 10], [0, 1])[1];
}

// Function to compare two strategies
function compareStrategies(strategy1, strategy2, n, strategy1Name, strategy2Name) {
    const result1 = strategy1(n);
    const result2 = strategy2(n);
    if (result1 !== result2) {
        console.error(`Discrepancy found for n=${n} between ${strategy1Name} and ${strategy2Name}: ${result1} != ${result2}`);
        return false;
    }
    return true;
}

// Stress Test Function
function stressTest(maxIterations, maxN) {
    for (let iteration = 0; iteration < maxIterations; iteration++) {
        const n = Math.floor(Math.random() * (maxN + 1));
        console.log(`Testing with n = ${n}`);

        if (!compareStrategies(getFibonacciLastDigitNaive, getFibonacciLastDigit, n, 'Naive', 'Efficient') ||
            !compareStrategies(getFibonacciLastDigitFunctionalNaive, getFibonacciLastDigit, n, 'Functional Naive', 'Efficient') ||
            !compareStrategies(getFibonacciLastDigitFunctional, getFibonacciLastDigit, n, 'Functional', 'Efficient')) {
            return;
        }

        console.log("OK");
    }
}

// Uncomment below to run stress test
stressTest(100000, 40);

// Read input from stdin and print output
// readInput();

module.exports = getFibonacciLastDigit;

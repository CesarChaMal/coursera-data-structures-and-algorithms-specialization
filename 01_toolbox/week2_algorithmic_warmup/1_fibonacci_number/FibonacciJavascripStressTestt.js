function readInput() {
    const readline = require('readline');
    const rl = readline.createInterface({
        input: process.stdin,
        terminal: false
    });

    process.stdin.setEncoding('utf8');
    rl.on('line', readLine);
}

function readLine(line) {
    console.log(calcFib(parseInt(line, 10)));
    process.exit();
}

/// O(2^n) time complexity - Naive Recursive Approach
function calcFibNaive(n) {
    if (n <= 1) return n;
    return calcFibNaive(n - 1) + calcFibNaive(n - 2);
}

// O(n) time complexity - Efficient Iterative Approach
function calcFib(n) {
    if (n <= 1) return n;
    let prev = 0, current = 1;
    for (let i = 2; i <= n; i++) {
        [prev, current] = [current, prev + current];
    }
    return current;
}

// O(2^n) time complexity, functional approach (Naive)
function calcFibFunctionalNaive(n) {
    if (n <= 1) return n;
    return calcFibFunctionalNaive(n - 1) + calcFibFunctionalNaive(n - 2);
}

function* fibonacciGenerator() {
    let [a, b] = [0, 1];
    while (true) {
        yield a;
        [a, b] = [b, a + b];
    }
}

// O(n) time complexity, functional approach (Efficient)
function calcFibFunctional(n) {
    const fibGen = fibonacciGenerator();
    return Array.from({ length: n + 1 }, () => fibGen.next().value).pop();
}

/*
function calcFibFunctional(n) {
    return Array.from({ length: n + 1 }, (_, i) => i)
        .reduce(([prev, curr], i) => {
            return i < 2 ? [prev, curr] : [curr, prev + curr];
        }, [0, 1])[0];
}
*/

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

        if (!compareStrategies(calcFibNaive, calcFib, n, 'Naive', 'Efficient') ||
            !compareStrategies(calcFibFunctionalNaive, calcFib, n, 'Functional Naive', 'Efficient') ||
            !compareStrategies(calcFibFunctional, calcFib, n, 'Functional', 'Efficient')) {
            return;
        }

        console.log("OK");
    }
}
// Uncomment below to run stress test
stressTest(100000, 40);

// Read input from stdin and print output
// readInput();

module.exports = calcFib;

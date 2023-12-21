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

// O(2^n) time complexity - Naive Recursive Approach
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

// O(n) time complexity, functional approach (Efficient)
function calcFibFunctional(n) {
    return Array.from({ length: n + 1 })
        .reduce(([prev, curr], _, i) => [curr, prev + curr], [0, 1])[0];
}

readInput();

module.exports = calcFib;

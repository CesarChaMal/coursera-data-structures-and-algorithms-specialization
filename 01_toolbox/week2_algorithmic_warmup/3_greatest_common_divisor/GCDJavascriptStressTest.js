function readInput() {
    const readline = require('readline');
    const rl = readline.createInterface({
        input: process.stdin,
        terminal: false
    });

    rl.on('line', line => {
        if (line !== "\n") {
            const a = parseInt(line.toString().split(' ')[0], 10);
            const b = parseInt(line.toString().split(' ')[1], 10);

            console.log(gcd(a, b));
            process.exit();
        }
    });
}

// O(min(a, b)) time complexity
function gcd_naive(a, b) {
    let current_gcd = 1;
    for (let d = 2; d <= Math.min(a, b); d++) {
        if (a % d === 0 && b % d === 0) {
            current_gcd = d;
        }
    }
    return current_gcd;
}

// O(log(min(a, b))) time complexity
// Euclidean Algorithm
function gcd(a, b) {
    if (b === 0) {
        return a;
    } else {
        return gcd(b, a % b);
    }
}

function trampoline(fn) {
    return function(...args) {
        let result = fn(...args);
        while (typeof result === 'function') {
            result = result();
        }
        return result;
    };
}

// O(min(a, b)) time complexity
function gcdFunctionalNaive(a, b) {
    function gcdHelper(divisor, currentGcd) {
        if (divisor > Math.min(a, b)) {
            return currentGcd;
        }
        const newGcd = (a % divisor === 0 && b % divisor === 0) ? divisor : currentGcd;
        return () => gcdHelper(divisor + 1, newGcd);
    }

    return trampoline(gcdHelper)(2, 1);
}

// O(log(min(a, b))) time complexity
// Euclidean Algorithm
function gcdFunctional(a, b) {
    function euclidGcd(x, y) {
        return y === 0 ? x : euclidGcd(y, x % y);
    }
    return euclidGcd(a, b);
}

// Function to compare two strategies
function compareStrategies(strategy1, strategy2, a, b, strategy1Name, strategy2Name) {
    const result1 = strategy1(a, b);
    const result2 = strategy2(a, b);
    if (result1 !== result2) {
        console.error(`Discrepancy found for a=${a}, b=${b} between ${strategy1Name} and ${strategy2Name}: ${result1} != ${result2}`);
        return false;
    }
    return true;
}

// Stress Test Function
function stressTest(maxIterations, maxN) {
    for (let iteration = 0; iteration < maxIterations; iteration++) {
        const a = Math.floor(Math.random() * (maxN + 1));
        const b = Math.floor(Math.random() * (maxN + 1));
        console.log(`Testing with a = ${a}, b = ${b}`);

        if (!compareStrategies(gcd_naive, gcd, a, b, 'Naive', 'Efficient') ||
            !compareStrategies(gcdFunctionalNaive, gcd, a, b, 'Functional Naive', 'Efficient') ||
            !compareStrategies(gcdFunctional, gcd, a, b, 'Functional', 'Efficient')) {
            return;
        }

        console.log("OK");
    }
}

// Uncomment below to run stress test
stressTest(100000, 1000000);

// Uncomment to use readInput
// readInput();

module.exports = gcd;
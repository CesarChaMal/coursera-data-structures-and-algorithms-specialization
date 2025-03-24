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

// O(a * b) time complexity - Naive Approach
function lcm_naive1(a, b) {
    for (let l = 1; l <= a * b; ++l) {
        if (l % a === 0 && l % b === 0) {
            return l;
        }
    }
    return a * b;
}

// O(a * b) time complexity - Alternative Naive LCM
function lcm_naive2(a, b) {
    let lcm = 1;
    while (true) {
        if (lcm % a === 0 && lcm % b === 0) {
            return lcm;
        }
        lcm++;
    }
}

// O(log(min(a, b))) time complexity - Euclidean Algorithm for GCD
function gcd(a, b) {
    return b === 0 ? a : gcd(b, a % b);
}

// O(log(min(a, b))) time complexity - Efficient LCM using GCD
function lcm(a, b) {
    return (a * b) / gcd(a, b);
}

// O(a * b) time complexity - Naive Approach (Functional, Recursive)
function lcm_naiveFunctional(a, b) {
    const limit = a * b;

    function* lcmGenerator() {
        for (let current = 1; current <= limit; current++) {
            yield current;
        }
    }

    for (const current of lcmGenerator()) {
        if (current % a === 0 && current % b === 0) {
            return current;
        }
    }

    return limit;
}

// O(log(min(a, b))) time complexity - Euclidean Algorithm for GCD
const gcdFunctional = (a, b) => {
    const euclidGcd = (x, y) => y === 0 ? x : euclidGcd(y, x % y);
    return euclidGcd(a, b);
};

// O(log(min(a, b))) time complexity - Efficient LCM using GCD
const lcmFunctional = (a, b) => {
    return (a * b) / gcdFunctional(a, b);
};

// Function to compare two strategies
function compareStrategies(strategy1, strategy2, a, b, strategy1Name, strategy2Name) {
    const result1 = strategy1(a, b);
    const result2 = strategy2(a, b);
    console.log(`Comparing ${strategy1Name} and ${strategy2Name} for a = ${a}, b = ${b}: ${result1} vs ${result2}`);
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

        if (!compareStrategies(lcm_naive1, lcm, a, b, 'Naive1', 'Efficient') ||
            !compareStrategies(lcm_naive2, lcm, a, b, 'Naive2', 'Efficient') ||
            !compareStrategies(lcm_naiveFunctional, lcm, a, b, 'Functional Naive', 'Efficient') ||
            !compareStrategies(lcmFunctional, lcm, a, b, 'Functional', 'Efficient')) {
            return;
        }

        console.log("OK");
    }
}

// Uncomment below to run stress test
stressTest(100000, 1000000);

// readInput();

module.exports = { gcd, lcm_naive, lcm_naive2, lcm, lcm_naiveFunctional, lcmFunctional };
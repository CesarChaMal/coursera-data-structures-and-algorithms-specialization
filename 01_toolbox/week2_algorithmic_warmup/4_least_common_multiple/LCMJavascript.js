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
function lcm_naive(a, b) {
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

// O(a * b) time complexity - Naive Approach (Functional, Iterative)
const lcm_naiveFunctional = (a, b) => {
    const limit = a * b;

    const findLcm = (current) => {
        if (current > limit) return limit;
        if (current % a === 0 && current % b === 0) return current;
        return findLcm(current + 1);
    };

    return findLcm(1);
};

// O(log(min(a, b))) time complexity - Euclidean Algorithm for GCD
const gcdFunctional = (a, b) => {
    const euclidGcd = (x, y) => y === 0 ? x : euclidGcd(y, x % y);
    return euclidGcd(a, b);
};

// O(log(min(a, b))) time complexity - Efficient LCM using GCD
const lcmFunctional = (a, b) => {
    return (a * b) / gcdFunctional(a, b);
};

readInput();

module.exports = lcm;

function readInput() {
    const readline = require('readline');
    const rl = readline.createInterface({
        input: process.stdin,
        terminal: false
    });

    rl.on('line', line => {
        if (line !== "\n") {
            const n = parseInt(line.toString().split(' ')[0], 10);
            const m = parseInt(line.toString().split(' ')[1], 10);

            console.log(gcd(n, m));
            process.exit();
        }
    });
}

// O(N) time complexity
function getFibonacciHugeNaive1(n, m) {
    if (n <= 1) return n;

    let previous = 0;
    let current = 1;

    for (let i = 0; i < n - 1; ++i) {
        let tmp_previous = previous;
        previous = current;
        current = (tmp_previous + current) % m;
    }

    return current;
}

// O(N) time complexity
function getPisanoPeriod(m) {
    let previous = 0;
    let current = 1;
    let res = 0;

    for (let i = 0; i < m * m; i++) {
        let temp = current;
        current = (previous + current) % m;
        previous = temp;

        if (previous === 0 && current === 1) {
            res = i + 1;
            break;
        }
    }
    return res;
}

// O(N) time complexity
function getFibonacciHugeNaive2(n, m) {
    let pisanoPeriod = getPisanoPeriod(m);

    n = n % pisanoPeriod;

    let previous = 0;
    let current = 1;

    if (n <= 1) return n;

    for (let i = 0; i < n - 1; i++) {
        let temp = current;
        current = (previous + current) % m;
        previous = temp;
    }

    return current;
}

// O(N) time complexity
function getFibonacciHugeNaive3(n, m) {
    if (n <= 1) return n;

    let previous = 0;
    let current = 1;

    for (let i = 0; i < n - 1; ++i) {
        let next = (previous + current) % m;
        previous = current;
        current = next;
    }

    return current;
}

function matrixMultiply(a, b, m) {
    return [
        [
            (a[0][0] * b[0][0] + a[0][1] * b[1][0]) % m,
            (a[0][0] * b[0][1] + a[0][1] * b[1][1]) % m
        ],
        [
            (a[1][0] * b[0][0] + a[1][1] * b[1][0]) % m,
            (a[1][0] * b[0][1] + a[1][1] * b[1][1]) % m
        ]
    ];
}

function matrixPower(matrix, n, m) {
    if (n === 0) return [[1, 0], [0, 1]];
    if (n === 1) return matrix;

    let halfPower = matrixPower(matrix, Math.floor(n / 2), m);
    let sq = matrixMultiply(halfPower, halfPower, m);

    if (n % 2 === 0) {
        return sq;
    } else {
        return matrixMultiply(matrix, sq, m);
    }
}

// O(log N) time complexity
function getFibonacciHuge(n, m) {
    if (n <= 1) return n;

    let initialMatrix = [[1, 1], [1, 0]];
    let resultMatrix = matrixPower(initialMatrix, n - 1, m);
    return resultMatrix[0][0];
}

// Helper method for recursive Fibonacci
function fibHelper(n, prev, curr, m) {
    if (n === 0) return prev;
    return fibHelper(n - 1, curr, (prev + curr) % m, m);
}

// O(N) time complexity, Functional programming style
function getFibonacciHugeNaive1Functional(n, m) {
    return fibHelper(n, 0, 1, m);
}

// O(N) time complexity, Functional programming style
function getFibonacciHugeNaive2Functional(n, m) {
    let pisanoPeriod = getPisanoPeriod(m);
    return fibHelper(n % pisanoPeriod, 0, 1, m);
}

// O(log N) time complexity - Functional style
const getFibonacciHugeFunctional = (n, m) => {
    if (n <= 1) return n;

    const initialMatrix = [[1, 1], [1, 0]];
    const resultMatrix = matrixPower(initialMatrix, n - 1, m);
    return resultMatrix[0][0];
};

// Function to compare two strategies
function compareStrategies(strategy1, strategy2, m, n, strategy1Name, strategy2Name) {
    const result1 = strategy1(m, n);
    const result2 = strategy2(m, n);
    console.log(`Comparing ${strategy1Name} and ${strategy2Name} for a = ${m}, b = ${n}: ${result1} vs ${result2}`);
    if (result1 !== result2) {
        console.error(`Discrepancy found for a=${m}, b=${n} between ${strategy1Name} and ${strategy2Name}: ${result1} != ${result2}`);
        return false;
    }
    return true;
}

// Stress Test Function
function stressTest(maxIterations) {
    for (let iteration = 0; iteration < maxIterations; iteration++) {
        const n = Math.floor(Math.random() * 40); // n in range [0, 39]
        const m = Math.floor(Math.random() * 100) + 2; // m in range [2, 101]
        console.log(`Testing with n = ${n}, m = ${m}`);

        if (!compareStrategies(getFibonacciHugeNaive1, getFibonacciHuge, n, m, 'Naive1', 'Efficient') ||
            !compareStrategies(getFibonacciHugeNaive2, getFibonacciHuge, n, m, 'Naive2', 'Efficient') ||
            !compareStrategies(getFibonacciHugeNaive3, getFibonacciHuge, n, m, 'Naive3', 'Efficient') ||
            !compareStrategies(getFibonacciHugeNaive1Functional, getFibonacciHuge, n, m, 'Functional Naive1', 'Efficient') ||
            !compareStrategies(getFibonacciHugeNaive2Functional, getFibonacciHuge, n, m, 'Functional Naive2', 'Efficient') ||
            !compareStrategies(getFibonacciHugeFunctional, getFibonacciHuge, n, m, 'Functional', 'Efficient')) {
            return;
        }

        console.log("OK");
    }
}

// Uncomment below to run stress test
stressTest(100000);

// readInput();

module.exports = getFibonacciHuge;

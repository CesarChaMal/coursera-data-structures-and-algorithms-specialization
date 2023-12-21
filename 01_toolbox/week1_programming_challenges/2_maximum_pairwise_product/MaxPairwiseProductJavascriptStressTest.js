function readInput() {
    const readline = require('readline');
    const rl = readline.createInterface({
        input: process.stdin,
        terminal: false
    });

    process.stdin.setEncoding('utf8');
    rl.once('line', () => {
        rl.on('line', readLine);
    });
}

function readLine(line) {
    const arr = line.toString().split(' ').map(Number);
    console.log(maxPairwiseProduct(arr));
    process.exit();
}

// O(n^2) time complexity
function maxPairwiseProductNaive(numbers) {
    let max_product = 0;
    let n = numbers.length;

    for (let first = 0; first < n; ++first) {
        for (let second = first + 1; second < n; ++second) {
            max_product = Math.max(max_product, numbers[first] * numbers[second]);
        }
    }

    return max_product;
}

// O(n) time complexity
function maxPairwiseProduct(numbers) {
    let max_product = 0;
    let n = numbers.length;

    let max1 = Math.max(numbers[0], numbers[1]);
    let max2 = Math.min(numbers[0], numbers[1]);

    for (let i = 2; i < n; ++i) {
        if (numbers[i] > max1) {
            max2 = max1;
            max1 = numbers[i];
        } else if (numbers[i] > max2) {
            max2 = numbers[i];
        }
    }

    max_product = max1 * max2;
    return max_product;
}

// O(n log n) time complexity, functional approach
function maxPairwiseProductFunctionalNaive(numbers) {
    return numbers
        .slice() // create a copy of the array
        .sort((a, b) => b - a) // sort in descending order
        .slice(0, 2) // take the first two elements (the largest ones)
        .reduce((a, b) => a * b, 1); // multiply them together
}

// O(n) time complexity, functional approach
function maxPairwiseProductFunctional(numbers) {
    const [max1, max2] = numbers.reduce(([first, second], current) => {
        if (current > first) {
            return [current, first];
        } else if (current > second) {
            return [first, current];
        }
        return [first, second];
    }, [-Infinity, -Infinity]);

    return max1 * max2;
}

function compareStrategies(numbers, strategy1, strategy2, description) {
    const result1 = strategy1(numbers);
    const result2 = strategy2(numbers);
    if (result1 !== result2) {
        console.log("Discrepancy found:");
        console.log(`${description}: ${result1} != ${result2}`);
        return false;
    }
    return true;
}

// Stress test
function stressTest(maxIterations = 100000) {
    for (let iteration = 0; iteration < maxIterations; iteration++) {
        const n = Math.floor(Math.random() * 1000) + 2; // Array size between 2 and 1001
        // const n = Math.floor(Math.random() * 10000)  % 1_000+ 2; // Array size between 2 and 1001

        const numbers = Array.from({ length: n }, () => Math.floor(Math.random() * 100000));

        // Print the array size and content
        console.log(`Array size: ${n}`);
        console.log(`Array: ${numbers.join(' ')}`);

/*
        // Calculate results using different methods
        const resultNaive = maxPairwiseProductNaive(numbers);
        const resultEfficient = maxPairwiseProduct(numbers);
        const resultFunctionalNaive = getMaxPairwiseProductFunctionalNaive(numbers);
        const resultFunctional = maxPairwiseProductFunctional(numbers);

        // Compare results
        if (resultNaive !== resultEfficient || resultFunctional !== resultEfficient || resultFunctionalNaive !== resultEfficient) {
            console.log("Discrepancy found:");
            if (resultNaive !== resultEfficient) {
                console.log(`Naive vs Efficient: ${resultNaive} != ${resultEfficient}`);
            }
            if (resultFunctional !== resultEfficient) {
                console.log(`Functional vs Efficient: ${resultFunctional} != ${resultEfficient}`);
            }
            if (resultFunctionalNaive !== resultEfficient) {
                console.log(`Functional Naive vs Efficient: ${resultFunctionalNaive} != ${resultEfficient}`);
            }
            break;
        } else {
            console.log("OK");
        }
*/

        const strategiesToCompare = [
            { func: maxPairwiseProductNaive, description: "Naive vs Efficient" },
            { func: maxPairwiseProductFunctional, description: "Functional vs Efficient" },
            { func: maxPairwiseProductFunctionalNaive, description: "Functional Naive vs Efficient" }
        ];

        const efficientResult = maxPairwiseProduct(numbers);
        let allMatch = true;
        for (const { func, description } of strategiesToCompare) {
            if (!compareStrategies(numbers, func, () => efficientResult, description)) {
                allMatch = false;
                break;
            }
        }

        if (allMatch) {
            console.log("OK");
        } else {
            break;
        }

    }
}

// Uncomment to run the stress test
stressTest();

// readInput();

module.exports = maxPairwiseProduct;


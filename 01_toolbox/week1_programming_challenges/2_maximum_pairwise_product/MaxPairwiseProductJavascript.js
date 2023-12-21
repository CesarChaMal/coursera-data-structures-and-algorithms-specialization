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

function readLine (line) {
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
            max_product = Math.max(maxProduct, numbers[first] * numbers[second]);
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

readInput();

module.exports = maxPairwiseProduct;

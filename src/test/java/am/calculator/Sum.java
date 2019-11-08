package am.calculator;

class Sum {
    int first;
    int second;
    int expectedSum;

    private Sum(int first, int second, int expectedSum) {
        this.first = first;
        this.second = second;
        this.expectedSum = expectedSum;
    }

    static Sum from(int first, int second, int expectedSum) {
        return new Sum(first, second, expectedSum);
    }

}

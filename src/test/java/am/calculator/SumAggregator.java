package am.calculator;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

class SumAggregator implements ArgumentsAggregator {

    @Override
    public Sum aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
        int first = Integer.parseInt((String) argumentsAccessor.get(1));
        int second = Integer.parseInt((String) argumentsAccessor.get(2));
        int expectedSum = Integer.parseInt((String) argumentsAccessor.get(3));
        return Sum.from(first, second, expectedSum);
    }
}
